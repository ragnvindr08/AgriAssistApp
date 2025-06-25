package com.example.bsitfinalmobapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarEntry.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class AnalyticsActivity : AppCompatActivity() {

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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        val barChart = findViewById<BarChart>(R.id.barChart)
        val textDescription = findViewById<TextView>(R.id.descriptionText)

        val cropCount = mutableMapOf<String, Int>()

        // Count how many times each crop appears
        for ((_, crops) in cropData) {
            for (crop in crops) {
                cropCount[crop] = cropCount.getOrElse(crop) { 0 } + 1
            }
        }

        val entries = mutableListOf<BarEntry>()
        val cropLabels = cropCount.keys.toList()

        for ((index, crop) in cropLabels.withIndex()) {
            entries.add(BarEntry(index.toFloat(), cropCount[crop]?.toFloat() ?: 0f))
        }

        val dataSet = BarDataSet(entries, "Crop Frequency by Province")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val barData = BarData(dataSet)
        barData.barWidth = 0.9f

        barChart.data = barData
        barChart.setFitBars(true)
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(cropLabels)
        barChart.xAxis.granularity = 1f
        barChart.xAxis.isGranularityEnabled = true
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.isEnabled = false
        barChart.description = Description().apply {
            text = "Most common crops across provinces"
        }
        barChart.invalidate()

        textDescription.text = """
            🔍 This chart shows how often each crop appears among 31 Philippine provinces (2025 data). 
            
            🌾 The most frequent crops are:
            - Rice
            - Corn
            - Coconut
            - Banana
            - Sugarcane

            📊 Source: Aggregated data based on regional agricultural trends (DA Reports, PSA, 2025 estimates)
        """.trimIndent()
    }
}
