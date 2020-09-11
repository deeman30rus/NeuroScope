package com.theroom101.ui.sunsigncarousel

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.core.domain.SunSign
import com.theroom101.ui.R

internal class CarouselItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(sunSign: SunSign) {
        (itemView as ImageView).setImageResource(sunSign.icon)
    }
}

private val SunSign.icon: Int
    @DrawableRes
    get() = when(this) {
        SunSign.Capricorn -> R.drawable.ui_capricorn_icon
        SunSign.Aquarius ->  R.drawable.ui_aquarius_icon
        SunSign.Pisces -> R.drawable.ui_pisces_icon
        SunSign.Aries -> R.drawable.ui_aries_icon
        SunSign.Taurus -> R.drawable.ui_taurus_icon
        SunSign.Gemini -> R.drawable.ui_gemini_icon
        SunSign.Cancer -> R.drawable.ui_cancer_icon
        SunSign.Leo -> R.drawable.ui_leo_icon
        SunSign.Virgo -> R.drawable.ui_virgo_icon
        SunSign.Libra -> R.drawable.ui_libra_icon
        SunSign.Scropio -> R.drawable.ui_scorpio_icon
        SunSign.Ophiuchus -> R.drawable.ui_neptune_icon // todo переделать на правильную иконку
        SunSign.Sagittarius -> R.drawable.ui_sagittarius_icon
    }
