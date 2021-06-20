package com.delizarov.forecast.domain.interactors

import com.delizarov.forecast.data.ForecastRepository
import com.delizarov.forecast.domain.models.Forecast
import com.delizarov.forecast.domain.models.ForecastParams
import com.theroom101.core.domain.SuspendableUseCase

class GetForecastUseCase(
    private val repo: ForecastRepository,
): SuspendableUseCase<ForecastParams, Forecast>() {

    override suspend fun execute(params: ForecastParams): Forecast {
        return repo.getForecast(params)
    }
}