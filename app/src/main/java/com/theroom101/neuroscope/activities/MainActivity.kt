package com.theroom101.neuroscope.activities

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.theroom101.core.android.BaseActivity
import com.theroom101.neuroscope.R
import com.theroom101.ui.forecast.ForecastViewController
import com.theroom101.ui.sunsigncarousel.SunSignCarousel

class MainActivity : BaseActivity() {

    private val forecastView by ViewProperty<ViewGroup>(R.id.forecast_view_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initProperties()

        val viewController = ForecastViewController(forecastView)
    }
}