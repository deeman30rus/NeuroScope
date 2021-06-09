package com.theroom101.ui.chart

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.animation.addListener
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.theroom101.core.android.dp
import com.theroom101.core.android.dpF
import com.theroom101.core.android.spF
import com.theroom101.core.assertions.Asserts
import com.theroom101.core.log.DebugLog
import com.theroom101.ui.R

private const val ANIMATION_DURATION_MS = 100L

class ChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var animation: ValueAnimator? = null

    private val chart: List<ChartItem>
        get() = children.map { (it as ItemView).chartItem }.toList()

    init {
        orientation = VERTICAL
    }

    fun updateChart(newChart: List<ChartItem>) {
        animation?.cancel()
        animation = null

        if (checkCanAnimate(newChart)) softUpdate(newChart)
        else hardUpdate(newChart)
    }

    private fun hardUpdate(newChart: List<ChartItem>) {
        clean()

        for (item in newChart) {
            addView(ItemView(context, item.copy()))
        }

        requestLayout()
    }

    private fun softUpdate(newChart: List<ChartItem>) {
        Asserts.assertNull(animation)

        val copy = chart.toList()

        animation = ValueAnimator.ofObject(ChartEvaluator(), copy, newChart).apply {
            duration = ANIMATION_DURATION_MS
            interpolator = DecelerateInterpolator()

            addUpdateListener { animator ->
                val cur =
                    animator.animatedValue as? List<ChartItem> ?: error("animation went wrong")

                for (i in cur.indices) {
                    val child = getChildAt(i) as ItemView

                    child.chartItem = ChartItem(cur[i].label, cur[i].meter)
                }

                invalidateChildren()
            }

            addListener(
                onCancel = {
                    for (i in newChart.indices) {
                        val child = getChildAt(i) as ItemView

                        child.chartItem = ChartItem(newChart[i].label, newChart[i].meter)
                    }

                    invalidateChildren()
                }
            )
        }
        animation?.start()
    }

    private fun invalidateChildren() {
        for (child in children) {
            child.invalidate()
        }
    }

    private fun checkCanAnimate(newChart: List<ChartItem>): Boolean {
        if (chart.size != newChart.size) return false

        for (i in newChart.indices) {
            if (newChart[i].label != chart[i].label) return false
        }

        return true
    }

    private fun clean() {
        removeAllViews()
    }

    private class ChartEvaluator : TypeEvaluator<List<ChartItem>> {

        override fun evaluate(
            fraction: Float,
            startValue: List<ChartItem>,
            endValue: List<ChartItem>
        ): List<ChartItem> {
            return startValue.zip(endValue).map { (start, end) -> ChartItem(start.label, start.meter + (end.meter - start.meter) * fraction) }.toList()
        }
    }
}

@SuppressLint("ViewConstructor")
private class ItemView(
    context: Context,
    var chartItem: ChartItem
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
        val y = height / 2f + dpF(2)

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
