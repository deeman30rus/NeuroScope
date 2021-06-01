package com.theroom101.ui.chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.theroom101.core.android.dp
import com.theroom101.core.android.dpF
import com.theroom101.core.android.spF
import com.theroom101.ui.R

class ChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    fun updateChart(chart: List<ChartItem>) {
        clean()

        for (item in chart) {
            addView(ItemView(context, item))
        }

        requestLayout()
    }

    private fun clean() {
        removeAllViews()
    }
}

@SuppressLint("ViewConstructor")
private class ItemView(
    context: Context,
    private val chartItem: ChartItem
) : View(context) {

    private val textBarrier = 0.33f
    private val textMargin = dp(24)

    private val backLinePaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = dpF(4)
        color = ResourcesCompat.getColor(resources, R.color.core_white5, null)
    }

    private val fillLinePaint = Paint().apply {
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        strokeWidth = dpF(4)
        color = ResourcesCompat.getColor(resources, R.color.core_cornflower_blue, null)
    }

    private val glowLinePaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = dpF(7)
        color = ResourcesCompat.getColor(resources, R.color.core_cornflower_blue25, null)
        maskFilter = BlurMaskFilter(24f, BlurMaskFilter.Blur.NORMAL)
    }

    private val textPaint = Paint().apply {
        typeface = ResourcesCompat.getFont(context, R.font.gilroy_regular)
        color = ResourcesCompat.getColor(resources, R.color.core_white, null)
        textSize = spF(14)
        style = Paint.Style.FILL_AND_STROKE
    }

    init {
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(32))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawLabel(chartItem.label)
        canvas.drawMeter(chartItem.meter)
    }

    private fun Canvas.drawLabel(label: String) {
        val textWidth = textPaint.measureText(label)

        val x = width * textBarrier - textMargin - textWidth
        val y = height / 2f

        drawText(label, x, y, textPaint)
    }

    private fun Canvas.drawMeter(meter: Float) {
        val startX = width * textBarrier
        val endX = width - dpF(4)

        val y = height / 2f

        drawLine(startX, y, endX, y, backLinePaint)

        val meterStopX = startX + endX * (1 - textBarrier) * meter

        drawLine(startX, y, meterStopX, y, glowLinePaint)
        drawLine(startX, y, meterStopX, y, fillLinePaint)
    }
}
