package com.delizarov.feature_forecast.presentation

import com.delizarov.feature_forecast.domain.interactors.GetForecastUseCase
import com.delizarov.feature_forecast.domain.models.Forecast
import com.delizarov.feature_forecast.domain.models.ForecastParams
import com.delizarov.feature_forecast.domain.models.ForecastPeriod
import com.theroom101.core.android.Dispatchers
import com.theroom101.core.domain.SunSign
import com.theroom101.core.mvp.MvpPresenter
import com.theroom101.core.mvp.MvpView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface ForecastMvpView: MvpView {

    val forecastPeriod: ForecastPeriod

    val sunSign: SunSign

    fun showForecast(forecast: Forecast)
}

class ForecastPresenter(
    private val getForecastUseCase: GetForecastUseCase,
    private val dispatchers: Dispatchers,
): MvpPresenter<ForecastMvpView>() {

    private val mainScope = CoroutineScope(dispatchers.main)

    fun onDemandToShowForecast() {
        mainScope.launch {
            val forecast = withContext(dispatchers.io) { getForecastUseCase.execute(view.forecastParams) }

            view.showForecast(forecast)
        }
    }

    private val ForecastMvpView.forecastParams: ForecastParams
        get() = ForecastParams(
            period = forecastPeriod,
            sunSign = sunSign
        )
}

