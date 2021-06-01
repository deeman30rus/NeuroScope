package com.theroom101.ui.selector

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.use
import androidx.core.view.marginBottom
import com.theroom101.core.android.dp
import com.theroom101.core.android.dpF
import com.theroom101.core.android.spF
import com.theroom101.core.log.DebugLog
import com.theroom101.ui.R

private const val ANIMATION_DURATION = 160L // ms

private const val NOT_SELECTED = -1

class SelectorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val textColorSelector = ResourcesCompat.getColorStateList(resources, R.color.selector_view_colors, null)

    private val underline = Underline()

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

    private var animation: ValueAnimator? = null

    var onItemClicked: ((String) -> Unit)? = null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.SelectorView).use { a ->

            val values = a.getTextArray(R.styleable.SelectorView_sv_values)
                ?.map { it.toString() }
                ?.toList()?: error("Can't find specified string array")

            for ((i, variant) in values.withIndex()) {
                addView(
                    context.newTextView(variant, i, ::handleItemClicked)
                )
            }

            selected = a.getInt(R.styleable.SelectorView_sv_selected, NOT_SELECTED)
        }

        orientation = HORIZONTAL
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (selected == NOT_SELECTED) return
        val item = getChildAt(0)

        val xStart = if (underline.left == -1) item.left else underline.left
        val xEnd = if (underline.right == -1) item.right else underline.right

        val y = item.bottom + dpF(10)

        canvas.drawLine(xStart.toFloat() - dpF(2), y, xEnd + dpF(2), y, glowPaint)
        canvas.drawLine(xStart.toFloat(), y, xEnd.toFloat(), y, linePaint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        animation?.cancel()
    }

    private fun handleItemClicked(sender: View) {
        if (animation?.isRunning == true) return

        val tv = sender as TextView

        if (selected != NOT_SELECTED) {
            val curSelected = getChildAt(selected)

            curSelected.isSelected = false

            val from = underline.copy()
            val to = Underline(tv.left, tv.right)

            resetAnimation(from, to)
        } else {

            underline.left = tv.left
            underline.right = tv.right
        }

        tv.isSelected = true
        selected = tv.tag as Int

        onItemClicked?.invoke(tv.text.toString())
    }

    private fun resetAnimation(from: Underline, to: Underline) {
        fun decel(x: Float, h: Int) = (-h * x * (x - 2)).toInt()
        fun accel(x: Float, h: Int) = (h * x * x).toInt()

        animation?.cancel()

        animation = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = ANIMATION_DURATION

            interpolator
            addUpdateListener {
                val progress = it.animatedValue as Float

                if (from.left < to.left) {
                    underline.left = from.left + accel(progress, to.left - from.left)
                    underline.right = from.right + decel(progress, to.right - from.right)
                } else {
                    underline.left = from.left - decel(progress, from.left - to.left)
                    underline.right = from.right - accel(progress, from.right - to.right)
                }

                invalidate()
            }
        }

        animation?.start()
    }

    private fun Context.newTextView(caption: String, pos: Int, onClickListener: OnClickListener) =
        TextView(this).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    leftMargin = dp(14)
                    rightMargin = dp(14)
                    topMargin = dp(12)
                    bottomMargin = dp(12)
                }

            textSize = 14f
            setTextColor(textColorSelector)

            setOnClickListener { tv ->
                onClickListener.onClick(tv)
            }

            text = caption
            tag = pos

            typeface = ResourcesCompat.getFont(this@newTextView, R.font.gilroy_medium)
        }

    private data class Underline(
        var left: Int = -1,
        var right: Int = -1
    )
}