package com.theroom101.ui.sunsigncarousel

import android.content.Context
import android.content.res.Resources
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.core.android.dp
import com.theroom101.core.domain.SunSign
import com.theroom101.ui.INF

private const val VISIBLE_ITEMS_AMOUNT = 5

private val metrics = Resources.getSystem().displayMetrics

internal class CarouselAdapter(private val context: Context): RecyclerView.Adapter<CarouselItemViewHolder>() {

    private val itemWidth = metrics.widthPixels / VISIBLE_ITEMS_AMOUNT
    private val itemHeight = dp(72)
    private val padding = dp(21)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarouselItemViewHolder(
        ImageView(context).apply {
            layoutParams = ViewGroup.LayoutParams(itemWidth, itemHeight)
            setPadding(padding, padding, padding, padding)
        }
    )

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val size = SunSign.values().size
        val sunSign = SunSign.values()[position % size]

        holder.bind(sunSign)
    }

    override fun getItemCount() = INF
}
