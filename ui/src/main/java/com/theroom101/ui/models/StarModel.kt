package com.theroom101.ui.models

import android.graphics.Point
import com.theroom101.ui.utils.StarType

private const val INC = 0.005f

private const val LOW_THRESHOLD = INC * 1.1f
private const val HIGH_THRESHOLD = 1 - INC * 1.1f

internal class StarModel(
    val type: StarType,
    coordinates: Point,
    val size: Int,
    var alpha: Float = 1f,
    val active: Boolean = false,
    var isShining: Boolean = false,
) {

    val x = coordinates.x
    val y = coordinates.y

    fun dim() {
        alpha -= INC

        if (alpha < LOW_THRESHOLD) {
            isShining = true
        }
    }

    fun shine() {
        alpha += INC

        if (alpha > HIGH_THRESHOLD) {
            isShining = false
        }
    }
}


