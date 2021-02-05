package com.theroom101.ui.sunsigncarousel

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.core.domain.SunSign
import com.theroom101.ui.INF

internal class CarouselAdapter(private val context: Context) : RecyclerView.Adapter<CarouselItemViewHolder>() {

    private val horizontalPadding = (SunSignCarousel.ITEM_WIDTH - SunSignCarousel.ICON_WIDTH) / 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarouselItemViewHolder(newItemView())

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val size = SunSign.values().size
        val sunSign = SunSign.values()[position % size]

        holder.bind(sunSign)
    }

    override fun getItemCount() = INF

    private fun newItemView(): View {
        val lp = ViewGroup.MarginLayoutParams(SunSignCarousel.ITEM_WIDTH, WindowManager.LayoutParams.MATCH_PARENT)
        return ImageView(context).apply {
            layoutParams = lp

            setPadding(
                    horizontalPadding,
                    0,
                    horizontalPadding,
                    0
            )
        }
    }
}
