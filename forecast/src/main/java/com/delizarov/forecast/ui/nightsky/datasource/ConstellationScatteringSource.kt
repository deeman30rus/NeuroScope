package com.delizarov.forecast.ui.nightsky.datasource

typealias ConstellationScatteringChangedCallback = (Float) -> Unit

class ConstellationScatteringSource {

    var scatteriing: Float = 0f
        set(value) {
            if (field == value) return

            field = value

            scatteringChangedCallback?.invoke(field)
        }

    var scatteringChangedCallback: ConstellationScatteringChangedCallback? = null
}