package com.theroom101.ui.parallax.vm

import android.graphics.Point
import android.graphics.drawable.Drawable

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
