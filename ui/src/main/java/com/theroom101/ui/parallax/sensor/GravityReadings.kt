package com.theroom101.ui.parallax.sensor

import com.theroom101.core.physics.Vector
import kotlin.math.max
import kotlin.math.min

private const val MEASURE_RATE = 4.2f
private const val G = 9.81f

class GravityReadings(
    rate: Float
) {

    private val dtMeasured = 1000 / MEASURE_RATE
    private val dtExtrapolated = 1000 / rate

    private var dt: Float = dtMeasured

    private var prev = Vector()
    private var cur = Vector()

    fun correct(readings: Vector) {
        prev = cur
        cur = readings

        dt = dtMeasured
    }

    fun extrapolate() = synchronized(this) {
        val nextX = extrapolate(cur.x, prev.x)
        val nextY = extrapolate(cur.y, prev.y)
        val next = Vector(
            x = min(G, max(-G, nextX)),
            y = min(G, max(-G, nextY))
        )

        prev = cur
        cur = next

        dt = dtExtrapolated
    }

    fun value(): Vector {
        return cur
    }

    private fun extrapolate(cur: Float, prev: Float): Float = synchronized(this) {
        (cur - prev) * (dtExtrapolated + dtMeasured) / dtMeasured
    }
}