package com.theroom101.ui.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.theroom101.core.android.dp
import com.theroom101.ui.R
import kotlin.random.Random
import kotlin.random.nextInt

internal fun Context.starDrawable(type: Int): Drawable = when (type) {
    0 -> ContextCompat.getDrawable(this, R.drawable.ui_star1)
        ?: error("star 1 drawable resource not found")
    1 -> ContextCompat.getDrawable(this, R.drawable.ui_star2)
        ?: error("star 2 drawable resource not found")
    2 -> ContextCompat.getDrawable(this, R.drawable.ui_star3)
        ?: error("star 3 drawable resource not found")
    else -> error("Unknown start type $type")
}

internal fun starSize(layerNo: Int) = when(layerNo) {
    0 -> Random.nextInt(dp(4) .. dp(7))
    1 -> Random.nextInt(dp(7) .. dp(10))
    2 -> Random.nextInt(dp(10) .. dp(13))
    3 -> Random.nextInt(dp(13) .. dp(16))
    else -> error("Unsupported layer $layerNo")
}

