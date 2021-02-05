package com.theroom101.ui.sunsigncarousel

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.ui.INF

class SunSignCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        adapter = CarouselAdapter(context)

        LinearSnapHelper().attachToRecyclerView(this)
        scrollToPosition(INF / 2)

        addItemDecoration(CarouselMainItemDecoration(context))
    }
}
