package com.delizarov.feature_forecast.data

import com.delizarov.feature_forecast.domain.models.Forecast
import com.delizarov.feature_forecast.domain.models.ForecastImpact
import com.delizarov.feature_forecast.domain.models.ForecastParams
import com.theroom101.core.domain.Planets
import kotlinx.coroutines.delay
import java.util.*
import kotlin.random.Random

private const val TEXT_PLACEHOLDER = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."

interface ForecastRepository {

    suspend fun getForecast(params: ForecastParams): Forecast

    class Impl: ForecastRepository {

        private var words: List<String> = TEXT_PLACEHOLDER.split(" ")

        override suspend fun getForecast(params: ForecastParams): Forecast {
            if (Random.nextInt(100) > 80) {
                delay(2000)
            }

            val range = (Planets.values().indices).toList().shuffled()
            val sliceSize = Random.nextInt(1, 3)

            val text = words.slice(0 .. Random.nextInt(words.size / 2, words.size - 1)).shuffled().joinToString(" ")

            return Forecast(
                date = Date(),
                affectingPlanets = (0 until sliceSize).map { Planets.values()[range[it]] }.toList(),
                text = text,
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


