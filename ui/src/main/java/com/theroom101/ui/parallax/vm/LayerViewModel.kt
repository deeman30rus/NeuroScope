package com.theroom101.ui.parallax.vm

import com.theroom101.core.physics.Vector
import kotlin.math.min

/**
 * from mg = kx (spring model)
 * x = mg / k => a = m / k
 */
internal class LayerViewModel(
    private val a: Float,
    generator: () -> List<Star>
) {
    private var accel = Vector()

    private var progress = Progress()

    private var interpolator = Interpolator(Vector(), Vector())
    var gravity = Vector()
        set(value)  {
            interpolator = Interpolator(accel, value)

            field = value

            progress.reNew()
        }

    val stars = generator.invoke()
    private val active = stars.filter { it.active }

    val dx: Int
        get() = (accel.x * a).toInt()

    val dy: Int
        get() = (accel.y * a).toInt()

    fun update() {
        active.forEach { star ->
            if (star.dim) star.dim()
            else star.shine()
        }

        if (accel == gravity) return


        accel = interpolator.value(progress.value)
        progress.inc()
    }
}

private class Progress {

    var value = 0f
        private set

    fun reNew() {
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
