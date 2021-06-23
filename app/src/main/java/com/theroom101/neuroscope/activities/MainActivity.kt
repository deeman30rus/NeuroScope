package com.theroom101.neuroscope.activities

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import com.delizarov.forecast.ui.ForecastViewController
import com.delizarov.forecast.ui.nightsky.NightSkyView
import com.delizarov.forecast.ui.nightsky.NightSkyViewModel
import com.delizarov.forecast.ui.nightsky.datasource.ConstellationScatteringSource
import com.delizarov.forecast.ui.nightsky.datasource.ConstellationTransitionSource
import com.delizarov.forecast.ui.nightsky.datasource.GravitySource
import com.theroom101.core.android.BaseActivity
import com.theroom101.core.domain.SunSign
import com.theroom101.core.log.DebugLog
import com.theroom101.neuroscope.R
import com.theroom101.ui.sunsignbadge.SunSignBadgeView
import com.theroom101.ui.sunsigncarousel.SunSignCarousel

class MainActivity : BaseActivity() {

    override val layoutRes: Int
        get() = R.layout.app_a_main

    private val forecastView by ViewProperty<ViewGroup>(R.id.forecast_view_container)
    private val nightSkyView by ViewProperty<NightSkyView>(R.id.night_sky_view)
    private val badgeView by ViewProperty<SunSignBadgeView>(R.id.sunsign_badge)

    private val carousel by ViewProperty<SunSignCarousel>(R.id.sunsign_carousel)

    private val gravitySource by lazy { GravitySource(this) }
    private val scatteringSource by lazy { ConstellationScatteringSource() }
    private val transitionSource by lazy { ConstellationTransitionSource() }

    private val nightSkyViewModel by lazy {
        NightSkyViewModel(
            this,
            gravitySource,
            transitionSource,
            scatteringSource
        )
    }

    private val viewController by lazy {
        ForecastViewController(forecastView) { slideOffset ->
            val progress = if (slideOffset >= 0f) (1f - slideOffset) else (1f + slideOffset)

            scatteringSource.scatteriing = 1 - progress
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(metrics)

        DebugLog.default.log { "w: ${metrics.widthPixels} h: ${metrics.heightPixels}" }

        nightSkyViewModel.initialize(metrics.widthPixels, metrics.heightPixels)
        nightSkyViewModel.setCurrentSunSign(SunSign.Sagittarius)

        carousel.addOnScrollListener { cur, shift ->
            val to = if (shift >= 0) cur.next() else cur.prev()
            badgeView.translate(cur, to, shift)

            transitionSource.updateTransition(cur, shift)
        }
    }


    override fun onResume() {
        super.onResume()

        gravitySource.startUpdates()
        nightSkyView.bind(nightSkyViewModel)
        viewController.onViewCreated()
    }

    override fun onPause() {
        super.onPause()

        gravitySource.stopUpdates()
        nightSkyView.unbindVm()
        viewController.onViewDestroyed()
    }

}