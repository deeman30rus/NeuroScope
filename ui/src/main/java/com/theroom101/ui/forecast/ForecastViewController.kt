package com.theroom101.ui.forecast

import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ForecastViewController(
    private val forecastView: ViewGroup
) {

    private val behavior: BottomSheetBehavior<ViewGroup> =
        BottomSheetBehavior.from(forecastView)

    init {
        forecastView.setOnClickListener {
            if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }
}

