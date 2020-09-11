package com.theroom101.ui.sunsigncarousel

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.ui.INF

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
