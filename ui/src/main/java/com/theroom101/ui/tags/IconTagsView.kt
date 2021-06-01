package com.theroom101.ui.tags

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.theroom101.core.android.dp
import com.theroom101.ui.R

class IconTagsView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    private val tags = mutableListOf<IconTag>()

    init {
        orientation = HORIZONTAL
    }

    fun setTags(tags: List<IconTag>) {
        clear()

        this.tags.addAll(tags)
        for (tag in tags) {
            addView(
                context.newTagView(tag)
            )
        }

        requestLayout()
    }

    fun clear() {
        removeAllViews()
        tags.clear()
    }

    private fun Context.newTagView(tag: IconTag) = TextView(this).apply {
        layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            leftMargin = dp(12)
            rightMargin = dp(12)
            topMargin = dp(12)
            bottomMargin = dp(12)
        }

        val icon = ResourcesCompat.getDrawable(resources, tag.iconRes, null)
        setCompoundDrawables(icon, null, null, null)
        compoundDrawablePadding = dp(4)

        setTextColor(ResourcesCompat.getColor(resources, R.color.core_white50, null))
        textSize = 14f
        typeface = ResourcesCompat.getFont(this@newTagView, R.font.gilroy_regular)

        text = tag.tag
    }
}