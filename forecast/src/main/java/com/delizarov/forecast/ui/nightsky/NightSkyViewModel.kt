package com.delizarov.forecast.ui.nightsky

import android.content.Context
import com.delizarov.forecast.ui.nightsky.layers.ParallaxLayerViewModel
import com.delizarov.forecast.ui.nightsky.layers.starLayerVm
import com.delizarov.forecast.ui.nightsky.parallax.GravitySource
import com.theroom101.core.math.Vector

private const val LAYER_1_AREA = 1.08f
private const val LAYER_2_AREA = 1.16f
private const val LAYER_3_AREA = 1.24f
private const val LAYER_4_AREA = 1.32f

private val layerDescriptions = listOf(
    LayerDescription(LAYER_1_AREA, 18, listOf(0.15f, 0.15f, 0.7f)),
    LayerDescription(LAYER_2_AREA, 18, listOf(0.2f, 0.2f, 0.6f)),
    LayerDescription(LAYER_3_AREA, 18, listOf(0.4f, 0.4f, 0.2f)),
    LayerDescription(LAYER_4_AREA, 18, listOf(0.45f, 0.45f, 0.1f)),
)

/**
 * Mvvm view model to notify [NightSkyView] about [GravitySource] changes
 */
class NightSkyViewModel(
    private val context: Context
) {

    fun interface Observer {

        fun onStateChanged()
    }

    private val gravitySource = GravitySource(context)
    private val observers = mutableListOf<Observer>()

    var isBound = false
        private set

    val layers = mutableListOf<ParallaxLayerViewModel>()

    init {
        gravitySource.onStateChangedCallback = ::updateState
    }

    fun bind(observer: Observer, width: Int, height: Int) {
        layers.clear()
        layers.addAll(layerDescriptions.mapIndexed { i, descr ->
            val (factor, starsCount, distribution) = descr

            starLayerVm {
                this.layerNo = i
                this.context = this@NightSkyViewModel.context
                this.viewWidth = width
                this.viewHeight = height
                this.starsCount = starsCount
                this.factor = factor
                this.starsDistribution = distribution
            }
        })

        if (observers.isEmpty()) gravitySource.startUpdates()

        observers.add(observer)

        isBound = true
    }

    fun unbind(observer: Observer) {
        observers.remove(observer)

        if (observers.isEmpty()) gravitySource.stopUpdates()

        isBound = false
    }

    private fun updateState(gravity: Vector) {
        for (layer in layers) {
            layer.gravity = gravity
            layer.updateState()
        }

        for (observer in observers) {
            observer.onStateChanged()
        }
    }
}

private data class LayerDescription(
    val factor: Float,
    val maxStars: Int,
    val starsDistribution: List<Float>
)

