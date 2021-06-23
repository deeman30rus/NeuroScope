package com.delizarov.forecast.ui.nightsky.layers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.delizarov.forecast.ui.nightsky.StarDrawableFactory
import com.delizarov.forecast.ui.nightsky.constellation.Constellation
import com.delizarov.forecast.ui.nightsky.constellation.Constellations
import com.delizarov.forecast.ui.nightsky.stars.StarModel
import com.delizarov.forecast.ui.nightsky.stars.StarSize
import com.theroom101.core.android.dp
import com.theroom101.core.android.dpF
import com.theroom101.core.domain.SunSign
import com.theroom101.core.math.sign
import com.theroom101.ui.R
import kotlin.math.min

internal class ConstellationLayerViewModel(
    private val context: Context,
    width: Int,
    height: Int,
    a: Float
) : ParallaxLayerViewModel(a) {

    private val renderer = Renderer(width, height)
    private val state = ViewState()

    var scattering: Float
        get() = state.scattering
        set(value) {
            if (state.scattering == value) return
            state.scattering = value
            renderer.alpha = 1 - value
        }

    override fun updateState() = Unit

    override fun drawOnCanvas(canvas: Canvas) {
        renderer.drawFrame(canvas, state)
    }

    fun translate(from: SunSign, progress: Float) {
        with(state) {
            current = Constellations[from]
            next = if (progress > 0) Constellations[from.next()] else Constellations[from.prev()]
            shift = progress
        }
    }

    fun setSunSign(sign: SunSign) {
        state.current = Constellations[sign]
    }

    internal class ViewState {
        lateinit var current: Constellation
        lateinit var next: Constellation

        var scattering = 0f
        var shift = 0f
    }

    private inner class Renderer(
        val width: Int,
        val height: Int,
    ) {

        private val starDrawableFactory = StarDrawableFactory
        private val stars = Constellations
            .stars
            .map { model -> model to starDrawableFactory.createStarDrawable(model) }
            .toMap()

        private var k = 0f
        private var size = 0

        private val linePaint = Paint().apply {
            color = context.getColor(R.color.core_white25)
            isAntiAlias = true
            strokeWidth = dpF(1)
            style = Paint.Style.STROKE
        }

        var alpha: Float
            get() = linePaint.alpha / 127f
            set(value) {
                linePaint.alpha = (value * 127).toInt()
            }

        var topPadding = dp(100)

        fun drawFrame(canvas: Canvas, state: ViewState) {
            val current = state.current
            val next = state.next
            val shift = state.shift

            size = (min(width, height) * (0.8f + state.scattering)).toInt()
            k = size / dpF(100)

            val left = ((width - size) / 2 - shift * width).toInt()
            val top = topPadding - (dp(200) * state.scattering).toInt()

            drawConstellation(canvas, current, left, top)

            if (shift != 0f) {
                val nextLeft = left + width * sign(shift)
                drawConstellation(canvas, next, nextLeft, top)
            }
        }

        private fun drawConstellation(
            canvas: Canvas,
            constellation: Constellation,
            left: Int,
            top: Int
        ) {
            for (star in constellation.stars) {
                drawStar(canvas, star, left, top)
            }

            for (edge in constellation.edges) {
                val from = constellation.stars[edge.first]
                val to = constellation.stars[edge.second]

                drawEdge(canvas, from, to, left, top)
            }
        }

        private fun drawStar(canvas: Canvas, star: StarModel, left: Int, top: Int) {
            val alpha = (255 * star.alpha).toInt()

            val bias = star.size / 2

            val x = (star.x * k).toInt() + star.hBias
            val y = (star.y * k).toInt() + star.vBias

            val drawable = stars[star] ?: return

            drawable.setBounds(
                x + left - bias,
                y + top - bias,
                x + left + bias,
                y + top + bias
            )
            drawable.alpha = alpha

            drawable.draw(canvas)
        }

        private fun drawEdge(canvas: Canvas, from: StarModel, to: StarModel, left: Int, top: Int) {
            val startX = (from.x * k + left) + from.hBias
            val startY = (from.y * k + top) + from.vBias
            val endX = (to.x * k + left) + to.hBias
            val endY = (to.y * k + top) + to.vBias

            canvas.drawLine(startX, startY, endX, endY, linePaint)
        }

        private val StarModel.hBias: Int
            get() = when(size) {
                in StarSize.Small.range -> dx
                in StarSize.Medium.range -> (dx * 0.95f).toInt()
                in StarSize.Large.range -> (dx * 0.9f).toInt()
                in StarSize.ExtraLarge.range -> (dx * 0.8f).toInt()
                else -> dx
            }

        private val StarModel.vBias: Int
            get() = when(size) {
                in StarSize.Small.range -> dy
                in StarSize.Medium.range -> (dy * 0.95f).toInt()
                in StarSize.Large.range -> (dy * 0.9f).toInt()
                in StarSize.ExtraLarge.range -> (dy * 0.8f).toInt()
                else -> dy
            }
    }
}

