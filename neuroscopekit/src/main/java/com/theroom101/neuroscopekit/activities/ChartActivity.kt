package com.theroom101.neuroscopekit.activities

import android.os.Bundle
import android.view.View
import com.theroom101.core.android.BaseActivity
import com.theroom101.neuroscopekit.R
import com.theroom101.ui.chart.ChartItem
import com.theroom101.ui.chart.ChartView
import kotlin.random.Random

class ChartActivity: BaseActivity() {

    private val chartView by ViewProperty<ChartView>(R.id.chart)
    private val newChartBtn by ViewProperty<View>(R.id.random)

    private val chart: List<ChartItem> get() = listOf(
        ChartItem("Love", Random.nextFloat()),
        ChartItem("Career", Random.nextFloat()),
        ChartItem("Family", Random.nextFloat()),
        ChartItem("Business", Random.nextFloat()),
        ChartItem("Luck", Random.nextFloat()),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.kit_a_chart)

        initProperties()

        chartView.updateChart(chart)
        newChartBtn.setOnClickListener {
            chartView.updateChart(chart)
        }
    }
}