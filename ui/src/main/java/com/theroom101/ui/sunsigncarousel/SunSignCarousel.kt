package com.theroom101.ui.sunsigncarousel

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.core.android.dp
import com.theroom101.ui.INF

private const val VISIBLE_ITEMS_AMOUNT = 5

private val metrics = Resources.getSystem().displayMetrics

class SunSignCarousel @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val snapHelper = LinearSnapHelper()

    private val centerItemDecoration = CarouselMainItemDecoration(context)

    init {
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        adapter = CarouselAdapter(context)

        snapHelper.attachToRecyclerView(this)
        scrollToPosition(INF / 2)

        addItemDecoration(centerItemDecoration)

        addOnScrollListener(ScrollListener())
    }

    private inner class ScrollListener : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            centerItemDecoration.biasAngle += dx.toFloat() / ITEM_WIDTH * 45
        }
    }

    companion object {

        val ICON_WIDTH = dp(40)
        val ITEM_WIDTH = metrics.widthPixels / VISIBLE_ITEMS_AMOUNT
    }
}
