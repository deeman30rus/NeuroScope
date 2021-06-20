package com.delizarov.forecast.ui.nightsky.layers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import com.delizarov.forecast.ui.StarDrawableFactory
import com.delizarov.forecast.ui.nightsky.stars.StarModel
import com.delizarov.forecast.ui.nightsky.stars.StarSize
import com.delizarov.forecast.ui.nightsky.stars.StarType
import com.theroom101.core.assertions.Asserts
import com.theroom101.core.math.createArea
import com.theroom101.core.math.randomPoint
import kotlin.random.Random

internal class StarsLayerViewModel(
    a: Float,
    val stars: List<StarModel>
) : ParallaxLayerViewModel(a) {

    private val starDrawableFactory = StarDrawableFactory

    private val active = stars.filter { it.isShining }

    private val starDrawables = stars.map { it to starDrawableFactory.createStarDrawable(it) }.toMap()

    override fun updateState() {
        for (star in active) {
            if (star.isShining) star.shine()
            else (star.dim())
        }
    }

    override fun drawOnCanvas(canvas: Canvas) {
        for (star in stars) {
            val drawable = starDrawables[star] ?: error("Model to drawable mapping corrupted")

            val alpha = (255 * star.alpha).toInt()

            drawable.setBounds(
                star.x + dx,
                star.y + dy,
                star.x + dx + star.size,
                star.y + dy + star.size
            )
            drawable.alpha = alpha

            drawable.draw(canvas)
        }
    }

    class Builder {
        var layerNo: Int = -1
        lateinit var context: Context
        var viewWidth: Int = -1
        var viewHeight: Int = -1
        var starsCount: Int = -1
        var factor: Float = -1f
        lateinit var starsDistribution: List<Float>

        fun build(): StarsLayerViewModel {
            Asserts.assertTrue(layerNo != -1)
            Asserts.assertTrue(viewWidth != -1)
            Asserts.assertTrue(viewHeight != -1)
            Asserts.assertTrue(starsCount != -1)
            Asserts.assertTrue(factor != -1f)

            val center = Point(viewWidth / 2, viewHeight / 2)

            val layerWidth = (viewWidth * factor).toInt()
            val layerHeight = (viewHeight * factor).toInt()

            val layerArea = createArea(center, layerWidth, layerHeight)

            val deltaMax = (viewWidth * (factor - 1)) / 2

            val stars = (1..starsCount).map {
                val r = Random.nextFloat()
                val type = StarType.randomInDistribution(r, starsDistribution)

                StarModel(
                    type = type,
                    coordinates = layerArea.randomPoint(),
                    size = StarSize.fromInt(layerNo).size(),
                    alpha = Random.nextFloat(),
                    active = Random.nextBoolean(),
                    isShining = Random.nextBoolean()
                )
            }.toList()

            return StarsLayerViewModel(
                a = deltaMax / 5.5f,
                stars = stars
            )
        }
    }
}

internal fun starLayerVm(block: StarsLayerViewModel.Builder.() -> Unit) =
    StarsLayerViewModel.Builder().apply(block).build()