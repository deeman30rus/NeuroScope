package com.delizarov.feature_forecast.di

import android.content.Context
import com.delizarov.feature_forecast.data.ForecastRepository
import com.delizarov.feature_forecast.domain.interactors.GetForecastUseCase
import com.delizarov.feature_forecast.presentation.ForecastPresenter
import com.theroom101.core.android.Dispatchers

internal class ForecastIocContainer(
    val context: Context
) {
    private val dispatchers = Dispatchers()

    private val forecastRepository: ForecastRepository
        get() = ForecastRepository.Impl()

    private val getForecastUseCase: GetForecastUseCase
        get() = GetForecastUseCase(forecastRepository)

    val forecastPresenter: ForecastPresenter by lazy { ForecastPresenter(getForecastUseCase, dispatchers) }
}