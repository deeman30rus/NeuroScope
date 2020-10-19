package com.theroom101.ui.parallax.vm

import android.graphics.Point
import android.graphics.drawable.Drawable
import com.theroom101.core.physics.Vector

/**
 * from mg = kx (spring model)
 * x = mg / k => a = m / k
 */
internal class LayerViewModel(
    private val a: Float,
    generator: () -> List<Star>
) {
    private var delta = Vector()

    var gravity = Vector()
        set(value) {
            field = value
            delta = value * a
        }

    val stars = generator.invoke()
    private val active = stars.filter { it.active }

    val dx: Int
        get() = delta.x.toInt()

    val dy: Int
        get() = delta.y.toInt()

    fun update() {
        active.forEach { star ->
            if (star.dim) star.dim()
            else star.shine()
        }
    }
}

internal class Star(
    val drawable: Drawable,
    coordinates: Point,
    val size: Int,
    var alpha: Float,
    val active: Boolean,
    var dim: Boolean
) {

    val x = coordinates.x
    val y = coordinates.y

    fun dim() {
        alpha -= 0.03f

        if (alpha < 0.04) {
            dim = false
        }
    }

    fun shine() {
        alpha += 0.03f

        if (alpha > 0.96) {
            dim = true
        }
    }
}
