package com.delizarov.forecast.ui.nightsky.stars

import com.theroom101.core.android.dp
import kotlin.random.Random
import kotlin.random.nextInt

internal enum class StarSize(
    private val min: Int,
    private val max: Int
) {
    Small(dp(7), dp(11)),
    Medium(dp(12), dp(18)),
    Large(dp(19), dp(26)),
    ExtraLarge(dp(27), dp(32));

    val range = min .. max

    fun size() = Random.nextInt(min .. max)

    companion object {

        fun fromInt(num: Int): StarSize = values()[num]
    }
}