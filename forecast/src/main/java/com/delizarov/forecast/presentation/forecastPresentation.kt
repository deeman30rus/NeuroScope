package com.delizarov.forecast.presentation

import com.delizarov.forecast.domain.interactors.GetForecastUseCase
import com.delizarov.forecast.domain.models.Forecast
import com.delizarov.forecast.domain.models.ForecastParams
import com.delizarov.forecast.domain.models.ForecastPeriod
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

    fun showProgress(debounce: Long)

    fun hideProgress()
}

private const val PROGRESS_DEBOUNCE_MS = 200L

class ForecastPresenter(
    private val getForecastUseCase: GetForecastUseCase,
    private val dispatchers: Dispatchers,
): MvpPresenter<ForecastMvpView>() {

    private val mainScope = CoroutineScope(dispatchers.main)

    fun onDemandToShowForecast() {
        mainScope.launch {
            view.showProgress(PROGRESS_DEBOUNCE_MS)

            val forecast = withContext(dispatchers.io) { getForecastUseCase.execute(view.forecastParams) }

            view.hideProgress()

            view.showForecast(forecast)
        }
    }

    private val ForecastMvpView.forecastParams: ForecastParams
        get() = ForecastParams(
            period = forecastPeriod,
            sunSign = sunSign
        )
}

