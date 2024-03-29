package com.theroom101.ui.sunsigncarousel

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.core.android.dp
import com.theroom101.core.domain.SunSign
import com.theroom101.core.math.sign
import com.theroom101.ui.INF
import kotlin.math.abs

private val metrics = Resources.getSystem().displayMetrics

class SunSignCarousel @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    fun interface OnScrollListener {

        /**
         * @param cur currently selected [SunSign]
         * @param shift floating point value in range [0 .. 1] represents progress of shift
         */
        fun onScroll(cur: SunSign, shift: Float)
    }

    fun interface OnStopScrollingListener {

        fun onStopScroll(sunSign: SunSign)
    }

    private val stopScrollListeners = mutableListOf<OnStopScrollingListener>()
    private val scrollListeners = mutableListOf<OnScrollListener>()

    private val snapHelper = LinearSnapHelper()

    private val centerItemDecoration = CarouselMainItemDecoration(context)

    var radius = 1000 // вычислено эмпирически, см. NeuroscopeKit -> Math -> Arc
        private set

    val sunSign: SunSign
        get() {
            return SunSign.values()[(snapPosition + 2) % SunSign.values().size]
        }

    private var snapPosition: Int = INF / 2

    init {
        layoutManager = CarouselLayoutManager(context)
        adapter = CarouselAdapter(context)

        snapHelper.attachToRecyclerView(this)
        scrollToPosition(snapPosition)

        addItemDecoration(centerItemDecoration)
        addItemDecoration(CarouselAlphaDecoration())
        addItemDecoration(BottomOffsetDecoration())

        addOnScrollListener(RecyclerScrollListener())
    }

    fun addOnStopScrollListener(listener: OnStopScrollingListener) {
        stopScrollListeners.add(listener)
    }

    fun removeOnStopScrollListener(listener: OnStopScrollingListener) {
        stopScrollListeners.remove(listener)
    }

    fun addOnScrollListener(listener: OnScrollListener) {
        scrollListeners.add(listener)
    }

    fun removeOnScrollListener(listener: OnScrollListener) {
        scrollListeners.remove(listener)
    }

    private fun notifyListenersOnScrolling(progress: Float) {
        for (listener in scrollListeners) {
            listener.onScroll(sunSign, progress)
        }
    }

    private fun notifyListenersOnStopScrolling() {
        for (listener in stopScrollListeners) {
            listener.onStopScroll(sunSign)
        }
    }

    private inner class RecyclerScrollListener : RecyclerView.OnScrollListener() {

        private var accumulator = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            centerItemDecoration.biasAngle += dx.toFloat() / ITEM_WIDTH * 45

            handleScrolling(dx)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == SCROLL_STATE_IDLE) {
                notifyListenersOnStopScrolling()
            }
        }

        private fun handleScrolling(dx: Int) {
            accumulator += dx

            if (abs(accumulator) >= ITEM_WIDTH) {
                snapPosition += sign(accumulator)
                accumulator -= sign(accumulator) * ITEM_WIDTH
            }

            notifyListenersOnScrolling(accumulator.toFloat() / ITEM_WIDTH)
        }
    }

    companion object {

        const val VISIBLE_ITEMS_AMOUNT = 5

        val ICON_WIDTH = dp(40)
        val ITEM_WIDTH = metrics.widthPixels / VISIBLE_ITEMS_AMOUNT
    }
}
