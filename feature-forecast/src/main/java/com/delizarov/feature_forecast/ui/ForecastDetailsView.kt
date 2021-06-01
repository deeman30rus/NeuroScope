package com.delizarov.feature_forecast.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.delizarov.feature_forecast.R

class ForecastDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        val inflater = LayoutInflater.from(context)

        inflater.inflate(R.layout.feature_forecast_view, this)
    }
}