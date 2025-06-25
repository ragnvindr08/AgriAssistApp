package com.example.bsitfinalmobapp

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MapsActivity : AppCompatActivity() {

    private lateinit var mapWebView: WebView
    private lateinit var provinceSpinner: Spinner
    private lateinit var cropDescription: TextView

    private val provinceMapData = mapOf(
        "Select a province..." to Pair("", ""),

        "Isabela" to Pair(
            "https://www.google.com/maps?q=Isabela,Philippines&output=embed",
            "Main Crops: Rice, Corn, Mungbean\nIsabela is known for vast rice fields and modern irrigation systems. It is one of the top contributors to the national grain supply."
        ),

        "Iloilo" to Pair(
            "https://www.google.com/maps?q=Iloilo,Philippines&output=embed",
            "Main Crops: Rice, Corn, Sugarcane\nA leader in sustainable farming, Iloilo boasts productive flatlands and strong agricultural research institutions."
        ),

        "Negros Occidental" to Pair(
            "https://www.google.com/maps?q=Negros+Occidental,Philippines&output=embed",
            "Main Crops: Sugarcane, Rice, Banana\nThis province is known as the Sugar Capital of the Philippines, with a strong economy centered around sugar milling."
        ),

        "Camarines Sur" to Pair(
            "https://www.google.com/maps?q=Camarines+Sur,Philippines&output=embed",
            "Main Crops: Coconut, Rice, Abaca\nThanks to its rich volcanic soil and climate, the province thrives in traditional crops and natural fiber production."
        ),

        "Pangasinan" to Pair(
            "https://www.google.com/maps?q=Pangasinan,Philippines&output=embed",
            "Main Crops: Tobacco, Mango, Rice\nFamous for Carabao mangoes and large-scale rice fields, supported by the Agno River irrigation system."
        ),

        "South Cotabato" to Pair(
            "https://www.google.com/maps?q=South+Cotabato,Philippines&output=embed",
            "Main Crops: Pineapple, Corn, Banana\nKnown for highland fruit plantations and strong agribusiness support infrastructure."
        ),

        "Zamboanga del Sur" to Pair(
            "https://www.google.com/maps?q=Zamboanga+del+Sur,Philippines&output=embed",
            "Main Crops: Rubber, Coconut, Corn\nPromotes diversified farming, agroforestry, and sustainable rural development."
        ),

        "Agusan del Sur" to Pair(
            "https://www.google.com/maps?q=Agusan+del+Sur,Philippines&output=embed",
            "Main Crops: Corn, Banana, Oil Palm\nAn agricultural hub in Caraga region with expanding eco-friendly practices."
        ),

        "Cotabato" to Pair(
            "https://www.google.com/maps?q=Cotabato,Philippines&output=embed",
            "Main Crops: Corn, Rice, Banana\nA flatland province with farm mechanization and support from NGOs and LGUs."
        ),

        "Leyte" to Pair(
            "https://www.google.com/maps?q=Leyte,Philippines&output=embed",
            "Main Crops: Abaca, Coconut, Rice\nA resilient province that rebuilt its agriculture sector after Typhoon Yolanda."
        ),

        "Sultan Kudarat" to Pair(
            "https://www.google.com/maps?q=Sultan+Kudarat,Philippines&output=embed",
            "Main Crops: Corn, Banana, Sugarcane\nStrong in infrastructure and insurance for farmers, improving livelihoods."
        ),

        "Laguna" to Pair(
            "https://www.google.com/maps?q=Laguna,Philippines&output=embed",
            "Main Crops: Rice, Vegetables, Coconut\nHome to agricultural schools and innovations that support local farmers."
        ),

        "Tarlac" to Pair(
            "https://www.google.com/maps?q=Tarlac,Philippines&output=embed",
            "Main Crops: Rice, Sugarcane, Corn\nLarge plantations, cooperative farming, and advanced irrigation define its farming."
        ),

        "Quezon" to Pair(
            "https://www.google.com/maps?q=Quezon+Province,Philippines&output=embed",
            "Main Crops: Coconut, Banana, Root Crops\nProduces coconut oil and supports diverse agriculture from lowlands to hills."
        ),

        "Capiz" to Pair(
            "https://www.google.com/maps?q=Capiz,Philippines&output=embed",
            "Main Crops: Rice, Corn, Coconut\nAlso a seafood capital, blending aquaculture with traditional farming."
        ),

        "Abra" to Pair(
            "https://www.google.com/maps?q=Abra,Philippines&output=embed",
            "Main Crops: Corn, Tobacco, Root Crops\nMountainous areas support sustainable agro-tourism and traditional farming."
        ),

        "Batangas" to Pair(
            "https://www.google.com/maps?q=Batangas,Philippines&output=embed",
            "Main Crops: Coffee, Pineapple, Sugarcane\nA historical center of coffee growing, especially Barako coffee."
        ),

        "Albay" to Pair(
            "https://www.google.com/maps?q=Albay,Philippines&output=embed",
            "Main Crops: Abaca, Coconut, Rice\nFarming is adapted to Mayon volcano’s activity using diversified systems."
        ),

        "Aurora" to Pair(
            "https://www.google.com/maps?q=Aurora,Philippines&output=embed",
            "Main Crops: Coconut, Banana, Root Crops\nKnown for eco-agriculture and upland to coastal farming systems."
        ),

        "Antique" to Pair(
            "https://www.google.com/maps?q=Antique,Philippines&output=embed",
            "Main Crops: Rice, Coconut, Sugarcane\nSupports organic farming and local food security efforts."
        ),

        "Cagayan" to Pair(
            "https://www.google.com/maps?q=Cagayan,Philippines&output=embed",
            "Main Crops: Tobacco, Rice, Corn\nStrategically located with access to irrigation and post-harvest centers."
        ),

        "Bohol" to Pair(
            "https://www.google.com/maps?q=Bohol,Philippines&output=embed",
            "Main Crops: Coconut, Rice, Corn\nAgri-tourism is growing alongside irrigation and organic initiatives."
        ),

        "Samar" to Pair(
            "https://www.google.com/maps?q=Samar,Philippines&output=embed",
            "Main Crops: Abaca, Coconut, Banana\nSpecializes in natural fiber production and resilient farming."
        ),

        "Zambales" to Pair(
            "https://www.google.com/maps?q=Zambales,Philippines&output=embed",
            "Main Crops: Mango, Rice, Corn\nFarming benefits from tourism, exporting Carabao mangoes."
        ),

        "Masbate" to Pair(
            "https://www.google.com/maps?q=Masbate,Philippines&output=embed",
            "Main Crops: Corn, Rice, Coconut\nBalances livestock farming and crop production well."
        ),

        "Mindoro Occidental" to Pair(
            "https://www.google.com/maps?q=Occidental+Mindoro,Philippines&output=embed",
            "Main Crops: Rice, Corn, Banana\nAdvancing irrigation and post-harvest facilities to boost yields."
        ),

        "Surigao del Sur" to Pair(
            "https://www.google.com/maps?q=Surigao+del+Sur,Philippines&output=embed",
            "Main Crops: Banana, Coconut, Root Crops\nPromotes agroforestry and disaster-ready crop practices."
        ),

        "Marinduque" to Pair(
            "https://www.google.com/maps?q=Marinduque,Philippines&output=embed",
            "Main Crops: Coconut, Banana, Rice\nKnown for traditional farming and budding farm tourism spots."
        ),

        "Quirino" to Pair(
            "https://www.google.com/maps?q=Quirino,Philippines&output=embed",
            "Main Crops: Rice, Banana, Corn\nUpland farming supports indigenous and sustainable agriculture."
        ),

        "Kalinga" to Pair(
            "https://www.google.com/maps?q=Kalinga,Philippines&output=embed",
            "Main Crops: Rice, Coffee, Banana\nFamous for traditional rice terraces and community-driven farming."
        ),

        "La Union" to Pair(
            "https://www.google.com/maps?q=La+Union,Philippines&output=embed",
            "Main Crops: Tobacco, Rice, Mango\nDiversified farming boosted by mango exports and agri-tourism."
        ),

        "Northern Samar" to Pair(
            "https://www.google.com/maps?q=Northern+Samar,Philippines&output=embed",
            "Main Crops: Coconut, Abaca, Banana\nSupports fiber farming and rural livelihood development projects."
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        mapWebView = findViewById(R.id.map_webview)
        provinceSpinner = findViewById(R.id.province_spinner)
        cropDescription = findViewById(R.id.crop_description)

        mapWebView.webViewClient = WebViewClient()
        mapWebView.settings.javaScriptEnabled = true
        mapWebView.isFocusable = true
        mapWebView.isClickable = true

        val provinceList = provinceMapData.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinceList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        provinceSpinner.adapter = adapter

        provinceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedProvince = provinceList[position]
                val (mapUrl, crops) = provinceMapData[selectedProvince] ?: Pair("", "")

                if (mapUrl.isNotEmpty()) {
                    loadMap(mapUrl)
                    cropDescription.text = crops
                } else {
                    mapWebView.loadData("", "text/html", "UTF-8")
                    cropDescription.text = "Select a province to view crop information."
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun loadMap(url: String) {
        val html = """
            <html>
              <body style="margin:0;padding:0;">
                <iframe src="$url" width="100%" height="100%" style="border:0;" allowfullscreen loading="lazy"></iframe>
              </body>
            </html>
        """.trimIndent()

        mapWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }
}
