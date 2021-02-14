package com.theroom101.ui.parallax.vm

import android.graphics.Point
import android.graphics.drawable.Drawable

private const val INC = 0.005f

private const val LOW_THRESHOLD = INC * 1.1f
private const val HIGH_THRESHOLD = 1 - INC * 1.1f

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
        alpha -= INC

        if (alpha < LOW_THRESHOLD) {
            dim = false
        }
    }

    fun shine() {
        alpha += INC

        if (alpha > HIGH_THRESHOLD) {
            dim = true
        }
    }
}
