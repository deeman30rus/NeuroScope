package com.theroom101.ui.selector

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.use
import com.theroom101.core.android.dp
import com.theroom101.core.android.dpF
import com.theroom101.core.android.spF
import com.theroom101.core.log.DebugLog
import com.theroom101.ui.R

private const val NOT_SELECTED = -1

private val logger = DebugLog.default

class SelectorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val textColorSelector = resources.getColorStateList(R.color.selector_view_colors)

    private val variants = mutableListOf<String>()

    private var selected = NOT_SELECTED
        set(value) {
            if (field == value) return
            field = value

            invalidate()
        }

    private val linePaint = Paint().apply {
        color = resources.getColor(R.color.ui_selector_underline_color)
        strokeWidth = dpF(2)
        isAntiAlias = true
    }

    private val glowPaint = Paint().apply {
        set(linePaint)
        color = resources.getColor(R.color.ui_selector_underline_color)
        strokeWidth = dpF(5)
        maskFilter = BlurMaskFilter(15f, BlurMaskFilter.Blur.NORMAL)
    }

    var onItemClicked: ((String) -> Unit)? = null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.SelectorView).use { a ->

            val values = a.getTextArray(R.styleable.SelectorView_sv_values)
                ?.map { it.toString() }
                ?.toList()?: error("Can't find specified string array")

            variants.addAll(values)

            selected = a.getInt(R.styleable.SelectorView_sv_selected, NOT_SELECTED)
        }

        for ((i, variant) in variants.withIndex()) {
            addView(
                context.newTextView(variant, i, ::handleItemClicked)
            )
        }

        orientation = HORIZONTAL
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (selected == NOT_SELECTED) return
        val item = getChildAt(selected)

        val xStart = item.left
        val xEnd = item.right

        val y = item.bottom + dpF(10)

        canvas.drawLine(xStart.toFloat(), y, xEnd.toFloat(), y, glowPaint)
        canvas.drawLine(xStart + dpF(2), y, xEnd - dpF(2), y, linePaint)
    }

    private fun handleItemClicked(sender: View) {
        val tv = sender as TextView

        if (selected != NOT_SELECTED) {
            val curSelected = getChildAt(selected)

            curSelected.isSelected = false
            tv.isSelected = true
        }

        selected = tv.tag as Int

        onItemClicked?.invoke(tv.text.toString())
    }

    private fun Context.newTextView(caption: String, pos: Int, onClickListener: OnClickListener) =
        TextView(this).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    leftMargin = dp(14)
                    rightMargin = dp(14)
                }

            textSize = spF(10)
            setTextColor(textColorSelector)

            setOnClickListener { tv ->
                isSelected = true
                onClickListener.onClick(tv)
            }

            text = caption
            tag = pos
        }

}