package com.example.bsitfinalmobapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SimpleLineGraphView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var data: List<Float> = emptyList()
    private val linePaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 6f
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    private val pointPaint = Paint().apply {
        color = Color.RED
        strokeWidth = 10f
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val axisPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 3f
        isAntiAlias = true
    }

    fun setData(data: List<Float>) {
        this.data = data
        invalidate()  // redraw the graph
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (data.isEmpty()) return

        val width = width.toFloat()
        val height = height.toFloat()

        // Draw X and Y axis
        canvas.drawLine(50f, height - 50f, width - 20f, height - 50f, axisPaint) // X axis
        canvas.drawLine(50f, 20f, 50f, height - 50f, axisPaint) // Y axis

        // Find max and min values to scale points
        val maxValue = data.maxOrNull() ?: 0f
        val minValue = data.minOrNull() ?: 0f
        val range = maxValue - minValue
        val usableHeight = height - 70f // padding for top and bottom
        val usableWidth = width - 70f   // padding for left and right

        if (data.size == 1) {
            // Only one data point: draw single point
            val x = 50f + usableWidth / 2f
            val y = height - 50f - ((data[0] - minValue) / (if (range == 0f) 1f else range)) * usableHeight
            canvas.drawCircle(x, y, 8f, pointPaint)
            return
        }

        // Draw points and connect them with lines
        val stepX = usableWidth / (data.size - 1)

        var previousX = 50f
        var previousY = height - 50f - ((data[0] - minValue) / (if (range == 0f) 1f else range)) * usableHeight

        // Draw first point
        canvas.drawCircle(previousX, previousY, 8f, pointPaint)

        for (i in 1 until data.size) {
            val x = 50f + i * stepX
            val y = height - 50f - ((data[i] - minValue) / (if (range == 0f) 1f else range)) * usableHeight

            // Draw line from previous point to current point
            canvas.drawLine(previousX, previousY, x, y, linePaint)

            // Draw current point
            canvas.drawCircle(x, y, 8f, pointPaint)

            previousX = x
            previousY = y
        }
    }
}
