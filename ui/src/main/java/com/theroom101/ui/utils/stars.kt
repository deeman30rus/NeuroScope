package com.theroom101.ui.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.theroom101.core.android.dp
import com.theroom101.ui.R
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

    fun size() = Random.nextInt(min .. max)

    companion object {

        fun fromInt(num: Int): StarSize = values()[num]
    }
}

internal enum class StarType(@DrawableRes val drawableId: Int) {

    Star1(R.drawable.ui_star_type_1),
    Star2(R.drawable.ui_star_type_2),
    Constellation(R.drawable.ui_star_constellation);

    fun getDrawable(context: Context): Drawable {
        return ContextCompat.getDrawable(context, drawableId) ?: error("Drawable not for $drawableId not found")
    }

    companion object {

        fun randomInDistribution(rand: Float, distribution: List<Float>): StarType {
            var sum = 0f
            for (i in distribution.indices) {
                sum += distribution[i]

                if (rand <= sum) return values()[i]
            }

            error("bad distribution")
        }

    }
}

