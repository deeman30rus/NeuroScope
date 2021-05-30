package com.theroom101.ui.forecast

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.theroom101.ui.R
import com.theroom101.ui.sunsigncarousel.SunSignCarousel
import com.theroom101.ui.utils.intermediateColor
import kotlin.math.abs

class ForecastViewController(
    forecastView: ViewGroup,
    private val onSlideCallback: (Float) -> Unit
) {

    private val context = forecastView.context
    private val resources = forecastView.resources

    private val carousel: SunSignCarousel = forecastView.findViewById(R.id.sunsign_carousel)
    private val background: View = forecastView.findViewById(R.id.background)
    private val chevrone: BottomSheetTipView = forecastView.findViewById(R.id.tip)
    private val foresightPeriod: View = forecastView.findViewById(R.id.foresight_period)
    private val planetTags: View = forecastView.findViewById(R.id.planet_tags)
    private val forecastText: View = forecastView.findViewById(R.id.forecast)
    private val chart: View = forecastView.findViewById(R.id.chart)

    private val behavior: BottomSheetBehavior<ViewGroup> =
        BottomSheetBehavior.from(forecastView).also {
            it.addBottomSheetCallback(ForecastPopupCallback())
        }

    init {
        forecastView.setOnClickListener {
            if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun setBackgroundColor(@ColorInt color: Int) {
        background.setBackgroundColor(color)
    }

    private inner class ForecastPopupCallback: BottomSheetBehavior.BottomSheetCallback() {

        private val startColor = resources.getColor(R.color.ui_forecast_view_start_color)
        private val finishColor = resources.getColor(R.color.ui_forecast_view_finish_color)

        override fun onStateChanged(bottomSheet: View, newState: Int) = Unit

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            val color = (startColor to finishColor).intermediateColor(slideOffset)

            setBackgroundColor(color)

            chevrone.transition = abs(slideOffset)

            onSlideCallback(slideOffset)
        }
    }
}

