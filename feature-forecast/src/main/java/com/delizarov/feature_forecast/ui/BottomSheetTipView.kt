package com.delizarov.feature_forecast.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.theroom101.core.android.dp
import com.theroom101.ui.R
import com.theroom101.ui.utils.intermediateColor

private val TIP_WIDTH = dp(146)

internal class BottomSheetTipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val startColor = resources.getColor(com.delizarov.feature_forecast.R.color.forecast_tip_start_color)
    private val finishColor = resources.getColor(com.delizarov.feature_forecast.R.color.forecast_tip_end_color)

    private val tipRect = Rect()
    private val lineRect = Rect()

    private val chevronTip = ContextCompat.getDrawable(context, R.drawable.ui_chevrone_tip)
        ?: error("resource for chevron tip not found")
    private val shortLine = ContextCompat.getDrawable(context, R.drawable.ui_short_line)
        ?: error("resource for short line not found")

    private val bgPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    var transition = 0f
        set(value) {
            if (field == value) return

            field = value
            invalidate()
        }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        tipRect.set((width - TIP_WIDTH) / 2, 0, (width + TIP_WIDTH) / 2, height)
        chevronTip.bounds = tipRect

        lineRect.set((width - TIP_WIDTH) / 2, height - dp(16), (width + TIP_WIDTH) / 2, height - dp(14))
        shortLine.bounds = lineRect
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        chevronTip.alpha = (255 * (1 - transition)).toInt()
        chevronTip.draw(canvas)

        shortLine.alpha = (255 * transition).toInt()
        shortLine.draw(canvas)

        val color =  (startColor to finishColor).intermediateColor(transition)
        bgPaint.color = color

        canvas.drawPaint(bgPaint)
    }
}
