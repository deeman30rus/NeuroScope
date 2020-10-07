package com.theroom101.ui.sunsigncarousel

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.core.android.dp

private const val VISIBLE_ITEMS_COUNT = 7

class CarouselLayoutManager: RecyclerView.LayoutManager() {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)

        if (state.itemCount == 0) {
            removeAllViews()
            return
        }

        repeat(VISIBLE_ITEMS_COUNT) { i ->
            val scrap = recycler.getViewForPosition(i)
            addView(scrap)

            measureChildWithMargins(scrap, 0, 0)
        }
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(dp(72), dp(72))
    }

    override fun canScrollHorizontally() = true

    override fun canScrollVertically() = false

    override fun getItemCount() = VISIBLE_ITEMS_COUNT
}