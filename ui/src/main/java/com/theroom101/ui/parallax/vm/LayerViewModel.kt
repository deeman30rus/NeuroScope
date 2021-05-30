package com.theroom101.ui.parallax.vm

import android.content.Context
import android.graphics.Point
import com.theroom101.core.assertions.Asserts
import com.theroom101.core.math.Vector
import com.theroom101.core.math.createArea
import com.theroom101.core.math.randomPoint
import com.theroom101.ui.models.StarModel
import com.theroom101.ui.utils.StarSize
import com.theroom101.ui.utils.StarType
import kotlin.math.min
import kotlin.random.Random

/**
 * from mg = kx (spring model)
 * x = mg / k => a = m / k
 */
internal class LayerViewModel private constructor(
    private val a: Float,
    val stars: List<StarModel>
) {

    private var accel = Vector()

    private var progress = Progress()

    private var interpolator = Interpolator(Vector(), Vector())
    var gravity = Vector()
        set(value)  {
            interpolator = Interpolator(accel, value)

            field = value

            progress.renew()
        }

    private val active = stars.filter { it.isShining }

    val dx: Int
        get() = (accel.x * a).toInt()

    val dy: Int
        get() = (accel.y * a).toInt()

    fun update() {
        active.forEach { star ->
            if (star.isShining) star.shine()
            else star.dim()
        }

        if (accel == gravity) return


        accel = interpolator.value(progress.value)
        progress.inc()
    }

    class Builder {
        var layerNo: Int = -1
        lateinit var context: Context
        var viewWidth: Int = -1
        var viewHeight: Int = -1
        var starsCount: Int = -1
        var factor: Float = -1f
        lateinit var starsDistribution: List<Float>

        fun build(): LayerViewModel {
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

            return LayerViewModel(
                a = deltaMax / 5.5f,
                stars = stars
            )
        }
    }
}

internal fun layerViewModel(block: LayerViewModel.Builder.() -> Unit) = LayerViewModel.Builder().apply(block).build()

private class Progress {

    var value = 0f
        private set

    fun renew() {
        value = 0f
    }

    fun inc() {
        value = min(1f, value + 1f / 30)
    }
}

private class Interpolator(
    private val start: Vector,
    private val end: Vector
) {

    /**
     * calculates intermediate value between [start] and [end] with given progress
     * @param progress float value in range 0 .. 1
     */
    fun value(progress: Float): Vector {
        return start + (end - start) * progress
    }
}
