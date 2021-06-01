package com.delizarov.feature_forecast.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.theroom101.core.android.dp
import com.theroom101.ui.R

private val TIP_WIDTH = dp(146)

internal class BottomSheetTipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val tipRect = Rect()
    private val lineRect = Rect()

    private val chevronTip = ContextCompat.getDrawable(context, R.drawable.ui_chevrone_tip)
        ?: error("resource for chevron tip not found")
    private val shortLine = ContextCompat.getDrawable(context, R.drawable.ui_short_line)
        ?: error("resource for short line not found")

    var transition = 0f
        set(value) {
            if (field == value) return

            field = value
            invalidate()
        }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        tipRect.set((width - TIP_WIDTH) / 2, 0, (width + TIP_WIDTH) / 2, height * 2 / 3)
        chevronTip.bounds = tipRect

        lineRect.set((width - TIP_WIDTH) / 2, height * 2 / 3, (width + TIP_WIDTH) / 2, height)
        shortLine.bounds = lineRect
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        chevronTip.alpha = (255 * (1 - transition)).toInt()
        chevronTip.draw(canvas)

        shortLine.alpha = (255 * transition).toInt()
        shortLine.draw(canvas)
    }
}
