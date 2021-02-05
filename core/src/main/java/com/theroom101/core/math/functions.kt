package com.theroom101.core.math

import kotlin.math.exp
import kotlin.math.sqrt

/**
 * @param x random value
 * @param ev expected value
 * @param variance variance in gauss distribution
 *
 * @return probability of random variable
 */
fun gaussProb(x: Float, ev: Float, variance: Float): Double {
    return exp(-1f / 2f * (x - ev) * (x - ev) / variance).toDouble()
}

fun arc(x: Int, x0: Int, r: Int): Int {
    return (-sqrt((r * r - (x - x0) * (x - x0)).toDouble()) + r).toInt()
}