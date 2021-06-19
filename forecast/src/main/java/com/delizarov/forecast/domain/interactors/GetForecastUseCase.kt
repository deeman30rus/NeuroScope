package com.delizarov.feature_forecast.domain.interactors

import com.delizarov.feature_forecast.data.ForecastRepository
import com.delizarov.feature_forecast.domain.models.Forecast
import com.delizarov.feature_forecast.domain.models.ForecastParams
import com.theroom101.core.domain.SuspendableUseCase

class GetForecastUseCase(
    private val repo: ForecastRepository,
): SuspendableUseCase<ForecastParams, Forecast>() {

    override suspend fun execute(params: ForecastParams): Forecast {
        return repo.getForecast(params)
    }
}