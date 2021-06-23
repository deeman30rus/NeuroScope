package com.delizarov.forecast.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.delizarov.forecast.R
import com.delizarov.forecast.domain.models.Forecast
import com.delizarov.forecast.domain.models.ForecastImpact
import com.delizarov.forecast.domain.models.ForecastPeriod
import com.theroom101.core.domain.Planets
import com.theroom101.ui.chart.ChartItem
import com.theroom101.ui.chart.ChartView
import com.theroom101.ui.selector.SelectorView
import com.theroom101.ui.tags.IconTag
import com.theroom101.ui.tags.IconTagsView

typealias OnForecastPeriodChanged = (ForecastPeriod) -> Unit

class ForecastDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val planetTags: IconTagsView
    private val periodSelector: SelectorView
    private val forecastText: TextView
    private val forecastImpact: ChartView
    private val progressBar: ProgressBar

    val forecastPeriod: ForecastPeriod
        get() {
            return ForecastPeriod.fromString(context, periodSelector.selectedItem ?: error("no period selected"))
        }

    var forecastPeriodChangedListener: OnForecastPeriodChanged? = null

    var forecast: Forecast? = null
        set(value) {
            field = value

            if (value != null) {
                showPlanetTags(value.planetTags)
                showForecastText(value.text)
                showImpact(value.impact)
            }
        }

    var isLoading = false
        set(value) {
            if (field == value) return

            field = value

            if (field) {
                progressBar.visibility = View.VISIBLE
                forecastText.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                forecastText.visibility = View.VISIBLE
            }
        }

    init {
        val inflater = LayoutInflater.from(context)

        inflater.inflate(R.layout.feature_forecast_view, this)

        periodSelector = findViewById<SelectorView>(R.id.period_selector).apply {
            onItemClicked = { periodName ->
                val period = ForecastPeriod.fromString(context, periodName)

                forecastPeriodChangedListener?.invoke(period)
            }
        }

        planetTags = findViewById(R.id.planet_tags)
        forecastText = findViewById(R.id.forecast_text)
        forecastImpact = findViewById(R.id.forecast_impact)
        progressBar = findViewById(R.id.loading)
    }

    private fun showPlanetTags(tags: List<IconTag>) {
        planetTags.setTags(tags)
    }

    private fun showForecastText(text: String) {
        forecastText.text = text
    }

    private fun showImpact(impact: ForecastImpact) {
        forecastImpact.updateChart(impact.toChart())
    }


    private val Forecast.planetTags: List<IconTag>
        get() = affectingPlanets.map { IconTag(it.drawable, it.planetName) }.toList()

    private val Planets.planetName: String get() = when (this) {
        Planets.Mercury -> context.getString(R.string.core_planet_mercury_name)
        Planets.Venus -> context.getString(R.string.core_planet_venus_name)
        Planets.Earth -> context.getString(R.string.core_planet_earth_name)
        Planets.Mars -> context.getString(R.string.core_planet_mars_name)
        Planets.Jupiter -> context.getString(R.string.core_planet_jupiter_name)
        Planets.Saturn -> context.getString(R.string.core_planet_saturn_name)
        Planets.Uranus -> context.getString(R.string.core_planet_uranus_name)
        Planets.Neptune -> context.getString(R.string.core_planet_neptune_name)
    }

    private val Planets.drawable: Int get() = when (this) {
        Planets.Mercury -> R.drawable.core_ic_mercury
        Planets.Venus -> R.drawable.core_ic_venus
        Planets.Earth -> R.drawable.core_ic_earth
        Planets.Mars -> R.drawable.core_ic_mars
        Planets.Jupiter -> R.drawable.core_ic_jupiter
        Planets.Saturn -> R.drawable.core_ic_saturn
        Planets.Uranus -> R.drawable.core_ic_uranus
        Planets.Neptune -> R.drawable.core_ic_neptune
    }

    private fun ForecastImpact.toChart(): List<ChartItem> = listOf(
        ChartItem(context.getString(R.string.forecast_impact_career), career),
        ChartItem(context.getString(R.string.forecast_impact_love), love),
        ChartItem(context.getString(R.string.forecast_impact_family), family),
        ChartItem(context.getString(R.string.forecast_impact_business), business),
        ChartItem(context.getString(R.string.forecast_impact_luck), luck),
    )
}