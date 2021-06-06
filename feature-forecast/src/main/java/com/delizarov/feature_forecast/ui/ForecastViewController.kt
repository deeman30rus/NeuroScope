package com.delizarov.feature_forecast.ui

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.delizarov.feature_forecast.R
import com.delizarov.feature_forecast.di.ForecastIocContainer
import com.delizarov.feature_forecast.domain.models.Forecast
import com.delizarov.feature_forecast.domain.models.ForecastPeriod
import com.delizarov.feature_forecast.presentation.ForecastMvpView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.theroom101.core.domain.SunSign
import com.theroom101.ui.sunsigncarousel.SunSignCarousel
import com.theroom101.ui.utils.intermediateColor
import kotlin.math.abs

class ForecastViewController(
    forecastView: ViewGroup,
    private val onSlideCallback: (Float) -> Unit
) {
    private var forecastPeriod: ForecastPeriod

    private val iocContainer = ForecastIocContainer(forecastView.context)

    private val resources = forecastView.resources
    private val carousel: SunSignCarousel = forecastView.findViewById(R.id.sunsign_carousel)

    private val forecastDetailsView: ForecastDetailsView =
        forecastView.findViewById<ForecastDetailsView>(R.id.background).apply {
            this@ForecastViewController.forecastPeriod = forecastPeriod

            forecastPeriodChangedListener = {
                this@ForecastViewController.forecastPeriod = it
            }
        }

    private val chevrone: BottomSheetTipView = forecastView.findViewById(R.id.tip)

    private val behavior: BottomSheetBehavior<ViewGroup> =
        BottomSheetBehavior.from(forecastView).also {
            it.addBottomSheetCallback(ForecastPopupCallback())
        }

    private val presenter = iocContainer.forecastPresenter
    private val mvpView = ForecastMvpViewImpl()

    fun onViewCreated() {
        presenter.attachView(mvpView)

        presenter.onDemandToShowForecast()
    }

    fun onViewDestroyed() {
        presenter.detachView()
    }

    private fun setBackgroundColor(@ColorInt color: Int) {
        forecastDetailsView.setBackgroundColor(color)
    }

    private inner class ForecastPopupCallback : BottomSheetBehavior.BottomSheetCallback() {

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

    private inner class ForecastMvpViewImpl : ForecastMvpView {

        override val forecastPeriod: ForecastPeriod
            get() = this@ForecastViewController.forecastPeriod

        override val sunSign: SunSign
            get() = carousel.sunSign

        override fun showForecast(forecast: Forecast) {
            forecastDetailsView.forecast = forecast
        }
    }
}

