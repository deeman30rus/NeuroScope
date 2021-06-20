package com.delizarov.forecast.ui.nightsky.layers

import android.graphics.Canvas
import com.theroom101.core.math.Vector

/**
 * [NightSkyView] Layer view model
 *
 * from mg = kx (spring model)
 * x = mg / k => a = m / k
 */
abstract class ParallaxLayerViewModel(
    private val a: Float,
) {
    var gravity = Vector()

    val dx: Int
        get() = (a * gravity.x).toInt()

    val dy: Int
        get() = (a * gravity.y).toInt()

    abstract fun updateState()

    abstract fun drawOnCanvas(canvas: Canvas)
}