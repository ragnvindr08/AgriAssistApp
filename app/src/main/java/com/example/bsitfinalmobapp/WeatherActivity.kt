package com.example.bsitfinalmobapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class WeatherActivity : AppCompatActivity() {

    private val apiKey = "153fb8064e20460aa7b150958251106"

    private lateinit var weatherTextView: TextView
    private lateinit var spinnerProvince: Spinner
    private lateinit var chartTypeSpinner: Spinner
    private lateinit var barChart: BarChart
    private lateinit var lineChart: LineChart
    private lateinit var pieChart: PieChart
    private lateinit var contentLayout: View
    private lateinit var loadingImageView: ImageView
    private lateinit var txtNoData: TextView

    private var chartEntries = mutableListOf<BarEntry>()

    private val agriProvinces = mapOf(
        "Nueva Ecija" to "Rice, Onion, Corn",
        "Bukidnon" to "Pineapple, Sugarcane, Banana",
        "Davao" to "Banana, Coconut, Cacao",
        "Benguet" to "Lettuce, Carrot, Cabbage",
        "Isabela" to "Rice, Corn, Mungbean",
        "Iloilo" to "Rice, Corn, Sugarcane",
        "Negros Occidental" to "Sugarcane, Rice, Banana",
        "Camarines Sur" to "Coconut, Rice, Abaca",
        "Pangasinan" to "Tobacco, Mango, Rice",
        "South Cotabato" to "Pineapple, Corn, Banana",
        "Zamboanga del Sur" to "Rubber, Coconut, Corn",
        "Agusan del Sur" to "Corn, Banana, Oil Palm",
        "Cotabato" to "Corn, Rice, Banana",
        "Leyte" to "Abaca, Coconut, Rice",
        "Sultan Kudarat" to "Corn, Banana, Sugarcane",
        "Laguna" to "Rice, Vegetables, Coconut",
        "Tarlac" to "Rice, Sugarcane, Corn",
        "Quezon" to "Coconut, Banana, Root Crops",
        "Capiz" to "Rice, Corn, Coconut",
        "Abra" to "Corn, Tobacco, Root Crops",
        "Batangas" to "Coffee, Pineapple, Sugarcane",
        "Albay" to "Abaca, Coconut, Rice",
        "Aurora" to "Coconut, Banana, Root Crops",
        "Antique" to "Rice, Coconut, Sugarcane",
        "Cagayan" to "Tobacco, Rice, Corn",
        "Bohol" to "Coconut, Rice, Corn",
        "Samar" to "Abaca, Coconut, Banana",
        "Zambales" to "Mango, Rice, Corn",
        "Masbate" to "Corn, Rice, Coconut",
        "Mindoro Occidental" to "Rice, Corn, Banana",
        "Surigao del Sur" to "Banana, Coconut, Root Crops",
        "Marinduque" to "Coconut, Banana, Rice",
        "Quirino" to "Rice, Banana, Corn",
        "Kalinga" to "Rice, Coffee, Banana",
        "La Union" to "Tobacco, Rice, Mango",
        "Northern Samar" to "Coconut, Abaca, Banana"
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        weatherTextView = findViewById(R.id.weatherTextView)
        spinnerProvince = findViewById(R.id.spinnerProvince)
        chartTypeSpinner = findViewById(R.id.chartTypeSpinner)
        barChart = findViewById(R.id.barChart)
        lineChart = findViewById(R.id.lineChart)
        pieChart = findViewById(R.id.pieChart)
        contentLayout = findViewById(R.id.contentLayout)
        loadingImageView = findViewById(R.id.loadingImageView)
        txtNoData = findViewById(R.id.txtNoData)

        Glide.with(this)
            .asGif()
            .load(R.drawable.loading)
            .into(loadingImageView)

        spinnerProvince.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            agriProvinces.keys.toList()
        )

        chartTypeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Bar Chart", "Line Chart", "Pie Chart")
        )

        chartTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                displayChart(pos)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        findViewById<Button>(R.id.btnSearch).setOnClickListener {
            fetchWeather(spinnerProvince.selectedItem.toString())
        }

        findViewById<Button>(R.id.btnBackToMain).setOnClickListener {
            finish()
        }

        // Initial state
        loadingImageView.visibility = View.GONE
        contentLayout.visibility = View.VISIBLE
    }

    private fun displayChart(type: Int) {
        barChart.visibility = View.GONE
        lineChart.visibility = View.GONE
        pieChart.visibility = View.GONE
        txtNoData.visibility = View.GONE

        if (chartEntries.isEmpty()) {
            txtNoData.visibility = View.VISIBLE
            return
        }

        when (type) {
            0 -> {
                val dataSet = BarDataSet(chartEntries, "Avg Temp (°C)").apply {
                    colors = ColorTemplate.COLORFUL_COLORS.toList()
                }
                barChart.data = BarData(dataSet)
                barChart.visibility = View.VISIBLE
                barChart.invalidate()
            }
            1 -> {
                val lineEntries = chartEntries.map { Entry(it.x, it.y) }
                val dataSet = LineDataSet(lineEntries, "Avg Temp (°C)").apply {
                    colors = ColorTemplate.JOYFUL_COLORS.toList()
                    setCircleColors(*ColorTemplate.MATERIAL_COLORS)
                }
                lineChart.data = LineData(dataSet)
                lineChart.visibility = View.VISIBLE
                lineChart.invalidate()
            }
            2 -> {
                val pieEntries = chartEntries.mapIndexed { index, e ->
                    PieEntry(e.y, "Day ${index + 1}")
                }
                val dataSet = PieDataSet(pieEntries, "Avg Temp (°C)").apply {
                    colors = ColorTemplate.MATERIAL_COLORS.toList()
                }
                pieChart.data = PieData(dataSet)
                pieChart.visibility = View.VISIBLE
                pieChart.invalidate()
            }
        }
    }

    private fun fetchWeather(city: String) {
        loadingImageView.visibility = View.VISIBLE
        contentLayout.visibility = View.GONE

        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)

            try {
                val url = URL("https://api.weatherapi.com/v1/forecast.json?key=$apiKey&q=$city&days=3")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                if (connection.responseCode == 200) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonObj = JSONObject(response)

                    val location = jsonObj.getJSONObject("location")
                    val cityName = location.getString("name")
                    val country = location.getString("country")

                    val forecast = jsonObj.getJSONObject("forecast").getJSONArray("forecastday")
                    val tips = StringBuilder()
                    chartEntries = mutableListOf()

                    for (i in 0 until forecast.length()) {
                        val day = forecast.getJSONObject(i)
                        val date = day.getString("date")
                        val dayData = day.getJSONObject("day")
                        val avgTemp = dayData.getDouble("avgtemp_c").toFloat()
                        val humidity = dayData.getInt("avghumidity")
                        val condition = dayData.getJSONObject("condition").getString("text")

                        chartEntries.add(BarEntry(i.toFloat(), avgTemp))

                        tips.append("📅 $date: $condition, ${avgTemp}°C, Humidity: $humidity%\n")
                        if (avgTemp > 32) tips.append("🌞 Too hot! Consider early irrigation.\n")
                        if (humidity > 85) tips.append("💧 High humidity – monitor for fungal disease.\n\n")
                    }

                    val crops = agriProvinces[city] ?: "Various crops"

                    withContext(Dispatchers.Main) {
                        weatherTextView.text = "🌾 $cityName, $country\nCrops: $crops\n\n$tips"
                        displayChart(chartTypeSpinner.selectedItemPosition)
                        loadingImageView.visibility = View.GONE
                        contentLayout.visibility = View.VISIBLE
                    }

                } else {
                    withContext(Dispatchers.Main) {
                        weatherTextView.text = "Error: ${connection.responseMessage}"
                        loadingImageView.visibility = View.GONE
                        contentLayout.visibility = View.VISIBLE
                    }
                }

                connection.disconnect()

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    weatherTextView.text = "Slow data connection please try again"
                    loadingImageView.visibility = View.GONE
                    contentLayout.visibility = View.VISIBLE
                }
            }
        }
    }
}
