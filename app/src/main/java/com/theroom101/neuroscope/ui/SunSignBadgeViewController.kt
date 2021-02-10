package com.theroom101.neuroscope.ui

import com.theroom101.core.domain.SunSign
import com.theroom101.ui.sunsignbadge.SunSignBadgeView
import com.theroom101.ui.sunsigncarousel.SunSignCarousel

class SunSignBadgeViewController(
        private val badgeView: SunSignBadgeView,
        startSign: SunSign
) : SunSignCarousel.OnScrollListener {

    init {
        badgeView.setSunSign(startSign)
    }

    override fun onScroll(cur: SunSign, shift: Float) {
        val to = if (shift >= 0) cur.next() else cur.prev()
        badgeView.translate(cur, to, shift)
    }
}