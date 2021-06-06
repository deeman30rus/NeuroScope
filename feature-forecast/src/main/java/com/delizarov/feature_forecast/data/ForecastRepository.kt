package com.delizarov.feature_forecast.data

import com.delizarov.feature_forecast.domain.models.Forecast
import com.delizarov.feature_forecast.domain.models.ForecastImpact
import com.delizarov.feature_forecast.domain.models.ForecastParams
import com.theroom101.core.domain.Planets
import java.util.*
import kotlin.random.Random

interface ForecastRepository {

    suspend fun getForecast(params: ForecastParams): Forecast

    class Impl: ForecastRepository {

        override suspend fun getForecast(params: ForecastParams): Forecast {
            val range = (Planets.values().indices).toList().shuffled()
            val sliceSize = Random.nextInt(0, range.size)

            return Forecast(
                date = Date(),
                affectingPlanets = (0 until sliceSize).map { Planets.values()[it] }.toList(),
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                impact = ForecastImpact(
                    career = Random.nextFloat(),
                    love = Random.nextFloat(),
                    family = Random.nextFloat(),
                    business = Random.nextFloat(),
                    luck = Random.nextFloat()
                )
            )
        }
    }
}


