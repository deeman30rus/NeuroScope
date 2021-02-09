package com.theroom101.neuroscope.ui

import com.theroom101.core.domain.SunSign
import com.theroom101.ui.sunsignbadge.SunSignBadgeView
import com.theroom101.ui.sunsigncarousel.SunSignCarousel

class SunSignBadgeViewController(
    val badgeView: SunSignBadgeView
): SunSignCarousel.OnScrollListener {

    override fun onSunSignChanged(newSunSign: SunSign) {
        badgeView.setSunSign(newSunSign)
    }

    override fun onSunSignChanging(from: SunSign, to: SunSign, percent: Float) {
        badgeView.translate(from, to, percent)
    }
}