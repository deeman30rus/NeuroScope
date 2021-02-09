package com.theroom101.ui.sunsigncarousel

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
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

    interface OnScrollListener {

        fun onSunSignChanged(newSunSign: SunSign)

        fun onSunSignChanging(from: SunSign, to: SunSign, percent: Float)
    }

    private val scrollListeners = mutableListOf<OnScrollListener>()

    private val snapHelper = LinearSnapHelper()

    private val centerItemDecoration = CarouselMainItemDecoration(context)

    var radius = 1000 // вычислено эмпирически, см. NeuroscopeKit -> Math -> Arc
        private set

    var sign: SunSign = SunSign.Sagittarius
        private set

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

    fun addOnScrollListener(listener: OnScrollListener) {
        scrollListeners.add(listener)
    }

    fun removeOnScrollListener(listener: OnScrollListener) {
        scrollListeners.remove(listener)
    }

    private fun notifyListenersOnSnapChanged(sunSign: SunSign) {
        for (listener in scrollListeners) {
            listener.onSunSignChanged(sunSign)
        }
    }

    private fun notifyListenersOnScrolling(from: SunSign, to: SunSign, progress: Float) {
        for (listener in scrollListeners) {
            listener.onSunSignChanging(from, to, progress)
        }
    }

    private inner class RecyclerScrollListener : RecyclerView.OnScrollListener() {

        private var accumulator = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            centerItemDecoration.biasAngle += dx.toFloat() / ITEM_WIDTH * 45

            handleScrolling(dx)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == SCROLL_STATE_IDLE) {
                accumulator = 0

                handleScrollStopped()
            }
        }

        private fun handleScrolling(dx: Int) {
            accumulator += dx

            if (abs(accumulator) >= ITEM_WIDTH) {
                accumulator = sign(accumulator) * (abs(accumulator) - ITEM_WIDTH)
                snapPosition += sign(dx)
            }

            val cur = snapPosition
            val next = cur + sign(dx)

            val progress = accumulator.toFloat() / ITEM_WIDTH

            notifyListenersOnScrolling(
                    from = SunSign.values()[cur % SunSign.values().size],
                    to = SunSign.values()[next % SunSign.values().size],
                    progress = progress
            )
        }

        private fun handleScrollStopped() {
            accumulator = 0

            snapPosition = snapHelper.getSnapPosition(this@SunSignCarousel)
            val sunSign = SunSign.values()[snapPosition % SunSign.values().size]

            notifyListenersOnSnapChanged(sunSign)
        }

        private fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
            val layoutManager = recyclerView.layoutManager ?: return NO_POSITION
            val snapView = findSnapView(layoutManager) ?: return NO_POSITION
            return layoutManager.getPosition(snapView)
        }
    }

    companion object {

        const val VISIBLE_ITEMS_AMOUNT = 5

        val ICON_WIDTH = dp(40)
        val ITEM_WIDTH = metrics.widthPixels / VISIBLE_ITEMS_AMOUNT
    }
}
