package com.theroom101.core.math

import kotlin.math.exp
import kotlin.math.sqrt

/**
 * Same as [kotlin.math.floor] but returns [Int] value
 */
fun floor(num: Float): Int {
    return kotlin.math.floor(num).toInt()
}

/**
 * Same as [kotlin.math.sign] but returns [Int] value
 */
fun sign(num: Int): Int {
    return when {
        num < 0 -> -1
        num > 0 -> 1
        else -> 0
    }
}

// todo move functions below to ui module

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