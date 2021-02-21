package com.theroom101.ui.zodiacview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.theroom101.core.android.dp
import com.theroom101.core.android.dpF
import com.theroom101.core.domain.SunSign
import com.theroom101.core.math.sign
import com.theroom101.ui.R
import com.theroom101.ui.parallax.vm.Star
import kotlin.math.min

/**
 * View to show transition on constellations on a background
 */
class ZodiacView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val renderer = Renderer(context)
    private val state = ViewState()

    private val constellationHolder = ConstellationHolder(context).apply {
        warmUp()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        renderer.updateBounds(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        renderer.drawFrame(canvas, state)
    }

    fun translate(from: SunSign, progress: Float) {
        with(state) {
            current = constellationHolder[from]
            next = if (progress > 0) constellationHolder[from.next()] else constellationHolder[from.prev()]
            shift = progress
        }

        invalidate()
    }

    fun setSunSign(sign: SunSign) {
        state.current = constellationHolder[sign]

        invalidate()
    }

    internal class ViewState {
        lateinit var current: Constellation
        lateinit var next: Constellation
        var shift = 0f
    }
}

private class Renderer(context: Context) {

    private var k = 0f
    private var size = 0

    private var width = 0
    private var height = 0

    private val linePaint = Paint().apply {
        color = context.getColor(R.color.core_white25)
        isAntiAlias = true
        strokeWidth = dpF(1)
        style = Paint.Style.STROKE
    }

    var topPadding = dp(100)

    fun updateBounds(w: Int, h: Int) {
        width = w
        height = h

        size = (min(w, h) * 0.8f).toInt()
        k = size / dpF(100)
    }

    fun drawFrame(canvas: Canvas, state: ZodiacView.ViewState) {
        val current = state.current
        val next = state.next
        val shift = state.shift

        val left = ((width - size) / 2 - shift * width).toInt()
        drawConstellation(canvas, current, left, topPadding)

        if (shift != 0f) {
            val nextLeft = left + width * sign(shift)
            drawConstellation(canvas, next, nextLeft, topPadding)
        }
    }

    private fun drawConstellation(canvas: Canvas, constellation: Constellation, left: Int, top: Int) {
        for(star in constellation.stars) {
            drawStar(canvas, star, left, top)
        }

        for (edge in constellation.edges) {
            val from = constellation.stars[edge.first]
            val to = constellation.stars[edge.second]

            drawEdge(canvas, from, to, left, top)
        }
    }

    private fun drawStar(canvas: Canvas, star: Star, left: Int, top: Int) {
        val alpha = (255 * star.alpha).toInt()

        val bias = star.size / 2

        val x = (star.x * k).toInt()
        val y = (star.y * k).toInt()

        star.drawable.setBounds(
            x + left - bias,
            y + top - bias,
            x + left + bias,
            y + top + bias
        )
        star.drawable.alpha = alpha

        star.drawable.draw(canvas)
    }

    private fun drawEdge(canvas: Canvas, from: Star, to: Star, left: Int, top: Int) {
        val startX = (from.x * k + left)
        val startY = (from.y * k + top)
        val endX = (to.x * k + left)
        val endY = (to.y * k + top)

        canvas.drawLine(startX, startY, endX, endY, linePaint)
    }
}