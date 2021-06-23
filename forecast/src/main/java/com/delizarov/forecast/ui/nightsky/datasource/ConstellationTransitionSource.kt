package com.delizarov.forecast.ui.nightsky.datasource

import com.theroom101.core.domain.SunSign

typealias OnTransitionChangedCallback = (SunSign, Float) -> Unit

class ConstellationTransitionSource {

    var currentSunSign = SunSign.Sagittarius
        private set

    var shift = 0f
        private set

    var transitionChangedCallback: OnTransitionChangedCallback? = null

    fun updateTransition(sunSign: SunSign, shift: Float) {
        this.currentSunSign = sunSign
        this.shift = shift

        transitionChangedCallback?.invoke(currentSunSign, shift)
    }
}