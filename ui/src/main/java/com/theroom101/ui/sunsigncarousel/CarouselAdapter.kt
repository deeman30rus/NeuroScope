package com.theroom101.ui.sunsigncarousel

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.core.android.dp
import com.theroom101.core.domain.SunSign
import com.theroom101.ui.INF

private const val VISIBLE_ITEMS_AMOUNT = 5

private val metrics = Resources.getSystem().displayMetrics

internal class CarouselAdapter(private val context: Context) : RecyclerView.Adapter<CarouselItemViewHolder>() {

    private val iconSize = dp(40)

    private val itemWidth = metrics.widthPixels / VISIBLE_ITEMS_AMOUNT

    private val horizontalPadding = (itemWidth - iconSize) / 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarouselItemViewHolder(newItemView())

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val size = SunSign.values().size
        val sunSign = SunSign.values()[position % size]

        holder.bind(sunSign)
    }

    override fun getItemCount() = INF

    private fun newItemView(): View {
        val lp = ViewGroup.MarginLayoutParams(itemWidth, WindowManager.LayoutParams.MATCH_PARENT)
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
