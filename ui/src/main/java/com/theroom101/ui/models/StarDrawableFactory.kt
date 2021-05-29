package com.theroom101.ui.models

import android.content.res.Resources
import android.graphics.drawable.Drawable
import com.theroom101.ui.utils.StarType

internal class StarDrawableFactory(
    private val resources: Resources
) {

    fun createStarDrawable(model: StarModel): Drawable {
        return resources.getDrawable(model.type).mutate()
    }

    private fun Resources.getDrawable(type: StarType) = getDrawable(type.drawableId)
}