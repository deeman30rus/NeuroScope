package com.theroom101.domain.ui.sunsigncarousel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.core.android.dp
import com.theroom101.core.domain.SunSign
import com.theroom101.domain.R

private const val INF = Int.MAX_VALUE

class SunSignCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        layoutManager = LinearLayoutManager(context).apply {
            orientation = HORIZONTAL
            scrollToPosition(INF / 2)
        }

        adapter = CarouselAdapter(context)
    }
}

private class CarouselAdapter(private val context: Context): RecyclerView.Adapter<CarouselItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarouselItemViewHolder(
        ImageView(context).apply {
            layoutParams = ViewGroup.LayoutParams(dp(72), dp(72))
        }
    )

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val size = SunSign.values().size
        val sunSign = SunSign.values()[position % size]

        holder.bind(sunSign)
    }

    override fun getItemCount() = INF
}

private class CarouselItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

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