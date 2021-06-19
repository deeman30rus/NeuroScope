package com.theroom101.ui.utils

import androidx.annotation.ColorInt

fun Pair<Int, Int>.intermediateColor(fraction: Float): Int {
    fun interpolate(s: Int, e: Int, f: Float) = (s + (e - s) * f).toInt()

    val start = Color.create(first)
    val finish = Color.create(second)

    val a = interpolate(start.alpha, finish.alpha, fraction)
    val r = interpolate(start.red, finish.red, fraction)
    val g = interpolate(start.green, finish.green, fraction)
    val b = interpolate(start.blue, finish.blue, fraction)

    return Color.create(a, r, g, b).colorInt
}

private class Color private constructor(@ColorInt val colorInt: Int) {

    val alpha = colorInt shr 24 and 0xff

    val red = colorInt shr 16 and 0xff

    val green = colorInt shr 8 and 0xff

    val blue = colorInt and 0xff

    companion object {

        fun create(@ColorInt colorInt: Int) = Color(colorInt)

        fun create(alpha: Int, red: Int, green: Int, blue: Int): Color {
            val colorInt = (alpha shl 24) or (red shl 16) or (green shl 8) or blue

            return create(colorInt)
        }
    }
}


