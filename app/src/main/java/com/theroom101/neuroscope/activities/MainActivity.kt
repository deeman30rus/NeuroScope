package com.theroom101.neuroscope.activities

import android.os.Bundle
import android.view.ViewGroup
import com.theroom101.core.android.BaseActivity
import com.theroom101.neuroscope.R
import com.theroom101.neuroscope.ui.ZodiacViewController
import com.theroom101.ui.forecast.ForecastViewController
import com.theroom101.ui.sunsignbadge.SunSignBadgeView
import com.theroom101.ui.sunsigncarousel.SunSignCarousel
import com.theroom101.ui.zodiacview.ZodiacView

class MainActivity : BaseActivity() {

    private val forecastView by ViewProperty<ViewGroup>(R.id.forecast_view_container)
    private val badgeView by ViewProperty<SunSignBadgeView>(R.id.sunsign_badge)
    private val zodiacView by ViewProperty<ZodiacView>(R.id.zodiac_view)
    private val carousel by ViewProperty<SunSignCarousel>(R.id.sunsign_carousel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initProperties()

        val viewController = ForecastViewController(forecastView) { slideOffset ->
            val progress = if (slideOffset >= 0f) (1f - slideOffset) else (1f + slideOffset)

            zodiacView.scattering = 1 - progress
            zodiacView.alpha = progress
        }


        carousel.addOnScrollListener(ZodiacViewController(badgeView, zodiacView, carousel.sunSign))
    }
}