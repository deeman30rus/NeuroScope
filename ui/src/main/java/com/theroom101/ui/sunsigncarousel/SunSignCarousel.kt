package com.theroom101.ui.sunsigncarousel

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.core.android.dp
import com.theroom101.core.domain.SunSign
import com.theroom101.ui.INF

private val metrics = Resources.getSystem().displayMetrics

class SunSignCarousel @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val snapHelper = LinearSnapHelper()

    private val centerItemDecoration = CarouselMainItemDecoration(context)

    var radius = 1000 // вычислено эмпирически, см. NeuroscopeKit -> Math -> Arc

    init {
        layoutManager = CarouselLayoutManager(context)
        adapter = CarouselAdapter(context)

        snapHelper.attachToRecyclerView(this)
        scrollToPosition(INF / 2)

        addItemDecoration(centerItemDecoration)
        addItemDecoration(CarouselAlphaDecoration())
        addItemDecoration(BottomOffsetDecoration())

        addOnScrollListener(ScrollListener())
    }

    private inner class ScrollListener : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            centerItemDecoration.biasAngle += dx.toFloat() / ITEM_WIDTH * 45
        }
    }

    companion object {

        const val VISIBLE_ITEMS_AMOUNT = 5

        val ICON_WIDTH = dp(40)
        val ITEM_WIDTH = metrics.widthPixels / VISIBLE_ITEMS_AMOUNT
    }
}
