package com.example.bsitfinalmobapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

class MarketActivity : AppCompatActivity() {

    private lateinit var spinnerProvince: Spinner
    private lateinit var spinnerChartType: Spinner
    private lateinit var recyclerMarketPrices: RecyclerView
    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart
    private lateinit var loadingImage: ImageView
    private lateinit var btnBackToHome: Button

    private val cropData = mapOf(
        "Isabela" to listOf("Rice", "Corn", "Mungbean"),
        "Iloilo" to listOf("Rice", "Corn", "Sugarcane"),
        "Negros Occidental" to listOf("Sugarcane", "Rice", "Banana"),
        "Camarines Sur" to listOf("Coconut", "Rice", "Abaca"),
        "Pangasinan" to listOf("Tobacco", "Mango", "Rice"),
        "South Cotabato" to listOf("Pineapple", "Corn", "Banana"),
        "Zamboanga del Sur" to listOf("Rubber", "Coconut", "Corn"),
        "Agusan del Sur" to listOf("Corn", "Banana", "Oil Palm"),
        "Cotabato" to listOf("Corn", "Rice", "Banana"),
        "Leyte" to listOf("Abaca", "Coconut", "Rice"),
        "Sultan Kudarat" to listOf("Corn", "Banana", "Sugarcane"),
        "Laguna" to listOf("Rice", "Vegetables", "Coconut"),
        "Tarlac" to listOf("Rice", "Sugarcane", "Corn"),
        "Quezon" to listOf("Coconut", "Banana", "Root Crops"),
        "Capiz" to listOf("Rice", "Corn", "Coconut"),
        "Abra" to listOf("Corn", "Tobacco", "Root Crops"),
        "Batangas" to listOf("Coffee", "Pineapple", "Sugarcane"),
        "Albay" to listOf("Abaca", "Coconut", "Rice"),
        "Aurora" to listOf("Coconut", "Banana", "Root Crops"),
        "Antique" to listOf("Rice", "Coconut", "Sugarcane"),
        "Cagayan" to listOf("Tobacco", "Rice", "Corn"),
        "Bohol" to listOf("Coconut", "Rice", "Corn"),
        "Samar" to listOf("Abaca", "Coconut", "Banana"),
        "Zambales" to listOf("Mango", "Rice", "Corn"),
        "Masbate" to listOf("Corn", "Rice", "Coconut"),
        "Mindoro Occidental" to listOf("Rice", "Corn", "Banana"),
        "Surigao del Sur" to listOf("Banana", "Coconut", "Root Crops"),
        "Marinduque" to listOf("Coconut", "Banana", "Rice"),
        "Quirino" to listOf("Rice", "Banana", "Corn"),
        "Kalinga" to listOf("Rice", "Coffee", "Banana"),
        "La Union" to listOf("Tobacco", "Rice", "Mango"),
        "Northern Samar" to listOf("Coconut", "Abaca", "Banana")
    )

    private val cropPrices2025 = mapOf(
        "Rice" to 48.50f,
        "Corn" to 21.75f,
        "Mungbean" to 95.00f,
        "Sugarcane" to 36.20f,
        "Banana" to 27.80f,
        "Coconut" to 16.90f,
        "Abaca" to 62.00f,
        "Tobacco" to 75.00f,
        "Mango" to 65.00f,
        "Pineapple" to 34.50f,
        "Rubber" to 42.00f,
        "Oil Palm" to 38.75f,
        "Vegetables" to 30.00f,
        "Root Crops" to 24.50f,
        "Coffee" to 120.00f
    )

    private var currentItems: List<Pair<String, Float>> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)

        spinnerProvince = findViewById(R.id.spinnerProvince)
        spinnerChartType = findViewById(R.id.spinnerChartType)
        recyclerMarketPrices = findViewById(R.id.recyclerMarketPrices)
        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.pieChart)
        loadingImage = findViewById(R.id.loadingImage)
        btnBackToHome = findViewById(R.id.btnBackToHome)

        recyclerMarketPrices.layoutManager = LinearLayoutManager(this)

        val provinces = cropData.keys.toList()
        val chartTypes = listOf("Bar Chart", "Pie Chart")
        val daWebsiteTextView: TextView = findViewById(R.id.tvDAWebsite)
        daWebsiteTextView.setOnClickListener {
            val url = "https://www.da.gov.ph/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }


        spinnerProvince.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, provinces)
        spinnerChartType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, chartTypes)

        spinnerProvince.setSelection(0)
        spinnerChartType.setSelection(0)

        Glide.with(this).asGif().load(R.drawable.loading2).into(loadingImage)

        spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                simulateLoadingAndUpdate()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerChartType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                showChartWithLoading()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnBackToHome.setOnClickListener {
            finish()
        }

        initialLoading()
    }

    private fun initialLoading() {
        spinnerProvince.visibility = View.GONE
        spinnerChartType.visibility = View.GONE
        recyclerMarketPrices.visibility = View.GONE
        barChart.visibility = View.GONE
        pieChart.visibility = View.GONE
        btnBackToHome.visibility = View.GONE
        loadingImage.visibility = View.VISIBLE

        loadingImage.postDelayed({
            loadingImage.visibility = View.GONE
            spinnerProvince.visibility = View.VISIBLE
            spinnerChartType.visibility = View.VISIBLE
            recyclerMarketPrices.visibility = View.VISIBLE
            btnBackToHome.visibility = View.VISIBLE
            updateMarketData()
        }, 500)
    }

    private fun simulateLoadingAndUpdate() {
        spinnerProvince.visibility = View.GONE
        spinnerChartType.visibility = View.GONE
        recyclerMarketPrices.visibility = View.GONE
        barChart.visibility = View.GONE
        pieChart.visibility = View.GONE
        btnBackToHome.visibility = View.GONE
        loadingImage.visibility = View.VISIBLE

        loadingImage.postDelayed({
            loadingImage.visibility = View.GONE
            spinnerProvince.visibility = View.VISIBLE
            spinnerChartType.visibility = View.VISIBLE
            recyclerMarketPrices.visibility = View.VISIBLE
            btnBackToHome.visibility = View.VISIBLE
            updateMarketData()
        }, 1500)
    }

    private fun updateMarketData() {
        val selectedProvince = spinnerProvince.selectedItem.toString()
        val crops = cropData[selectedProvince] ?: emptyList()
        currentItems = crops.map { crop -> crop to (cropPrices2025[crop] ?: 0f) }
        recyclerMarketPrices.adapter = MarketAdapter(currentItems)
        showChartWithLoading()
    }

    private fun showChartWithLoading() {
        barChart.visibility = View.GONE
        pieChart.visibility = View.GONE
        loadingImage.visibility = View.VISIBLE

        loadingImage.postDelayed({
            loadingImage.visibility = View.GONE
            showChart()
        }, 1000)
    }

    private fun showChart() {
        val chartType = spinnerChartType.selectedItem.toString()

        barChart.visibility = if (chartType == "Bar Chart") View.VISIBLE else View.GONE
        pieChart.visibility = if (chartType == "Pie Chart") View.VISIBLE else View.GONE

        if (chartType == "Bar Chart") {
            val entries = currentItems.mapIndexed { index, pair ->
                BarEntry(index.toFloat(), pair.second)
            }
            val dataSet = BarDataSet(entries, "Crop Prices (₱/kg)")
            dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
            val barData = BarData(dataSet)
            barChart.data = barData
            barChart.invalidate()
        } else if (chartType == "Pie Chart") {
            val pieEntries = currentItems.map { PieEntry(it.second, it.first) }
            val pieDataSet = PieDataSet(pieEntries, "Crop Prices (₱/kg)")
            pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
            val pieData = PieData(pieDataSet)
            pieChart.data = pieData
            pieChart.invalidate()
        }
    }

    inner class MarketAdapter(private val items: List<Pair<String, Float>>) :
        RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

        inner class MarketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textCrop: TextView = view.findViewById(android.R.id.text1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return MarketViewHolder(view)
        }

        override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
            holder.textCrop.visibility = View.INVISIBLE
            holder.textCrop.postDelayed({
                val item = items[position]
                holder.textCrop.text = "${item.first}: ₱${item.second}/kg"
                holder.textCrop.visibility = View.VISIBLE
            }, (position * 300L))
        }

        override fun getItemCount(): Int = items.size
    }

}
