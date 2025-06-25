package com.example.bsitfinalmobapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CropGuideActivity : AppCompatActivity() {

    private lateinit var provinceSpinner: Spinner
    private lateinit var guideTextView: TextView
    private lateinit var btnShowGuide: Button
    private lateinit var btnBackToMain: Button
    private lateinit var loadingImageView: ImageView
    private lateinit var contentLayout: LinearLayout

    private val cropGuides = mapOf(
        "Nueva Ecija" to """
        🌾 Rice
        Plant: June to July (start of rainy season)
        Water: Keep soil flooded during early growth
        Harvest: 100–120 days after sowing

        🌽 Corn
        Plant: March to May
        Water: Weekly deep watering
        Harvest: When husks are dry and kernels hard

        🧅 Onion
        Plant: October to December
        Water: Twice a week, avoid waterlogging
        Harvest: Tops fall and necks tighten
    """.trimIndent(),

        "Davao" to """
        🍌 Banana
        Plant: Year-round with sufficient rainfall
        Water: Every 2–3 days in dry season
        Harvest: 9–12 months after planting

        🥥 Coconut
        Plant: June to September
        Water: Every 2 weeks (early stage)
        Harvest: Every 45 days after 6 years

        🍫 Cacao
        Plant: Start of rainy season
        Water: Moderate; requires shade
        Harvest: 5–6 months after flowering
    """.trimIndent(),

        "Benguet" to """
        🥬 Lettuce
        Plant: Cold months (Oct–Feb)
        Water: Daily light watering
        Harvest: 30–40 days after planting

        🥕 Carrot
        Plant: Sept–Feb (high altitude)
        Water: 2–3 times per week
        Harvest: 2–3 months after sowing

        🥬 Cabbage
        Plant: October to March
        Water: Regularly in dry months
        Harvest: When heads are firm and full
    """.trimIndent(),

        "South Cotabato" to """
        🍍 Pineapple
        Plant: During rainy season
        Water: Every 3–4 days (young stage)
        Harvest: 18–24 months after planting

        🌽 Corn
        Plant: Start of dry season
        Water: Weekly
        Harvest: Kernels firm and husks brown

        🍌 Banana
        Plant: Anytime with irrigation
        Water: Consistently during dry season
        Harvest: 10–12 months after planting
    """.trimIndent(),

        "Isabela" to """
        🌾 Rice
        Plant: June or November
        Water: Maintain 2–5 cm water
        Harvest: 110 days after planting

        🌽 Corn
        Plant: March to May
        Water: 1 inch per week
        Harvest: Husk turns brown

        🌱 Mungbean
        Plant: After rice harvest
        Water: Lightly every 4–5 days
        Harvest: 60–75 days after sowing
    """.trimIndent(),

        "Iloilo" to """
        🌾 Rice
        Plant: May to June
        Water: Keep field moist
        Harvest: 110 days after sowing

        🌽 Corn
        Plant: February to April
        Water: Weekly
        Harvest: Kernel hard, husk dry

        🍬 Sugarcane
        Plant: Wet season
        Water: Only during dry months
        Harvest: 10–18 months after planting
    """.trimIndent(),

        "Negros Occidental" to """
        🍬 Sugarcane
        Plant: September to November
        Water: Minimal, only during drought
        Harvest: After 10–12 months

        🌾 Rice
        Plant: May to July
        Water: Flooded field
        Harvest: After 100 days

        🍌 Banana
        Plant: Year-round
        Water: Regularly during dry season
        Harvest: 10 months
    """.trimIndent(),

        "Camarines Sur" to """
        🥥 Coconut
        Plant: May to October
        Water: Moderate
        Harvest: Every 45 days after 6 years

        🌾 Rice
        Plant: June to July
        Water: Maintain 3–5 cm
        Harvest: 100–120 days

        🌿 Abaca
        Plant: Rainy season
        Water: Natural rainfall preferred
        Harvest: 18–24 months
    """.trimIndent(),

        "Pangasinan" to """
        🍃 Tobacco
        Plant: October to November
        Water: Every 2 days initially
        Harvest: 60–90 days after transplant

        🥭 Mango
        Plant: Any time
        Water: Weekly in dry season
        Harvest: March to May

        🌾 Rice
        Plant: May to June
        Water: Flooded field
        Harvest: 110–120 days
    """.trimIndent(),

        "Bukidnon" to """
        🍍 Pineapple
        Plant: Rainy season
        Water: 2x weekly when young
        Harvest: 18 months

        🍬 Sugarcane
        Plant: Start of wet season
        Water: Minimal
        Harvest: 10–18 months

        🍌 Banana
        Plant: Year-round
        Water: Every 2–3 days
        Harvest: 9–12 months
    """.trimIndent(),

        "Zamboanga del Norte" to """
🌶 Chili Pepper  
Plant: Late rainy season (Sept–Oct)  
Water: Twice a week  
Harvest: 2–3 months after sowing  

🥭 Mango  
Plant: Anytime  
Water: Weekly in dry months  
Harvest: March to May  

🌾 Rice  
Plant: May to July  
Water: Maintain shallow water  
Harvest: 3.5 months after planting
""".trimIndent(),

        "Palawan" to """
🥥 Coconut  
Plant: June to September  
Water: Moderate during early growth  
Harvest: Every 45 days after maturity  

🍍 Pineapple  
Plant: Start of rainy season  
Water: Twice a week  
Harvest: 18 months  

🥬 Pechay  
Plant: All year  
Water: Lightly every day  
Harvest: 25–30 days after sowing
""".trimIndent(),

        "Tarlac" to """
🌾 Rice  
Plant: June to August  
Water: Flooded field  
Harvest: After 110 days  

🌽 Corn  
Plant: Dry season  
Water: Weekly  
Harvest: 3 months after sowing  

🍉 Watermelon  
Plant: Late dry season  
Water: Deep watering every 5 days  
Harvest: 2–3 months
""".trimIndent(),

        "Zambales" to """
🥭 Mango  
Plant: Anytime  
Water: Every 7–10 days during dry months  
Harvest: March to May  

🌽 Corn  
Plant: March to May  
Water: Weekly  
Harvest: When husks are dry  

🍈 Melon  
Plant: End of dry season  
Water: Moderate, twice weekly  
Harvest: 2–3 months
""".trimIndent(),

        "Batangas" to """
🍆 Eggplant  
Plant: Sept–Nov  
Water: Twice a week  
Harvest: 90–100 days  

🍅 Tomato  
Plant: October to December  
Water: Regularly during flowering  
Harvest: 60–80 days  

🥭 Mango  
Plant: Any season  
Water: Bi-weekly in dry months  
Harvest: March to May
""".trimIndent(),


    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop_guide)

        provinceSpinner = findViewById(R.id.spinnerCropProvince)
        guideTextView = findViewById(R.id.txtCropGuide)
        btnShowGuide = findViewById(R.id.btnShowGuide)
        btnBackToMain = findViewById(R.id.btnBackToMain)
        loadingImageView = findViewById(R.id.loadingImageView)
        contentLayout = findViewById(R.id.contentLayout)

        Glide.with(this).asGif().load(R.drawable.crop).into(loadingImageView)

        provinceSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            cropGuides.keys.toList()
        )

        btnShowGuide.setOnClickListener {
            val selectedProvince = provinceSpinner.selectedItem.toString()

            loadingImageView.visibility = View.VISIBLE
            contentLayout.visibility = View.GONE

            CoroutineScope(Dispatchers.Main).launch {
                delay(4000) // simulate loading
                val guide = cropGuides[selectedProvince] ?: "No guide available for this province."

                guideTextView.text = guide
                loadingImageView.visibility = View.GONE
                contentLayout.visibility = View.VISIBLE
            }
        }

        btnBackToMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
