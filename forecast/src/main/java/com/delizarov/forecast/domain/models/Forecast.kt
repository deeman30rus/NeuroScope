package com.delizarov.feature_forecast.domain.models

import android.content.Context
import androidx.annotation.StringRes
import com.delizarov.feature_forecast.R
import com.theroom101.core.domain.Planets
import com.theroom101.core.domain.SunSign
import java.util.*

enum class ForecastPeriod(@StringRes val nameRes: Int) {
    Today(R.string.forecast_period_today),
    Tomorrow(R.string.forecast_period_tomorrow),
    ThisWeek(R.string.forecast_period_week);

    companion object {

        fun fromString(context: Context, str: String): ForecastPeriod {
            for (value in values()) {
                val name = context.getString(value.nameRes)

                if (str.equals(name, ignoreCase = true)) return value
            }

            error("period for name $str not found")
        }
    }
}

data class ForecastParams(
    val period: ForecastPeriod,
    val sunSign: SunSign,
)

data class ForecastImpact(
    val career: Float,
    val love: Float,
    val family: Float,
    val business: Float,
    val luck: Float
)


data class Forecast(
    val date: Date,
    val affectingPlanets: List<Planets>,
    val text: String,
    val impact: ForecastImpact
)