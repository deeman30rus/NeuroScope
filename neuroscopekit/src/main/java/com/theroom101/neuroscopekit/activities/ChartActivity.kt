package com.theroom101.neuroscopekit.activities

import android.os.Bundle
import com.theroom101.core.android.BaseActivity
import com.theroom101.neuroscopekit.R
import com.theroom101.ui.chart.ChartItem
import com.theroom101.ui.chart.ChartView

class ChartActivity: BaseActivity() {

    private val chartView by ViewProperty<ChartView>(R.id.chart)

    private val chart = listOf(
        ChartItem("Love", .5f),
        ChartItem("Career", .6f),
        ChartItem("Family", 1f),
        ChartItem("Business", .25f),
        ChartItem("Luck", .05f),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.kit_a_chart)

        initProperties()

        chartView.updateChart(chart)
    }
}