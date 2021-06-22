package com.delizarov.forecast.ui.nightsky

import android.content.Context
import com.delizarov.forecast.ui.nightsky.datasource.ConstellationScatteringSource
import com.delizarov.forecast.ui.nightsky.datasource.ConstellationTransitionSource
import com.delizarov.forecast.ui.nightsky.layers.ConstellationLayerViewModel
import com.delizarov.forecast.ui.nightsky.layers.ParallaxLayerViewModel
import com.delizarov.forecast.ui.nightsky.layers.starLayerVm
import com.delizarov.forecast.ui.nightsky.datasource.GravitySource
import com.theroom101.core.domain.SunSign
import com.theroom101.core.math.Vector

private const val LAYER_1_AREA = 1.08f
private const val LAYER_2_AREA = 1.16f
private const val LAYER_3_AREA = 1.24f
private const val LAYER_4_AREA = 1.32f

private val starLayerDescriptions = listOf(
    StarLayerDescription(LAYER_1_AREA, 18, listOf(0.15f, 0.15f, 0.7f)),
    StarLayerDescription(LAYER_2_AREA, 18, listOf(0.2f, 0.2f, 0.6f)),
    StarLayerDescription(LAYER_3_AREA, 18, listOf(0.4f, 0.4f, 0.2f)),
    StarLayerDescription(LAYER_4_AREA, 18, listOf(0.45f, 0.45f, 0.1f)),
)

/**
 * Mvvm view model to notify [NightSkyView] about [GravitySource] changes
 */
class NightSkyViewModel(
    private val context: Context,
    gravitySource: GravitySource,
    constellationTransitionSource: ConstellationTransitionSource,
    constellationScatteringSource: ConstellationScatteringSource,
) {

    fun interface Observer {

        fun onStateChanged()
    }

    private lateinit var constellationLayerVm: ConstellationLayerViewModel

    private val observers = mutableListOf<Observer>()

    val layers = mutableListOf<ParallaxLayerViewModel>()

    init {
        gravitySource.onStateChangedCallback = ::onGravityVectorChanged
        constellationTransitionSource.transitionChangedCallback = ::onConstellationTransitionChanged
        constellationScatteringSource.scatteringChangedCallback = ::onScatteringChanged
    }

    fun setCurrentSunSign(sunSign: SunSign) {
        constellationLayerVm.setSunSign(sunSign)
    }

    fun initialize(width: Int, height: Int) {
        createLayers(width, height)
    }

    fun observe(observer: Observer) {
        observers.add(observer)
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    private fun createLayers(width: Int, height: Int) {
        layers.clear()
        layers.addAll(starLayerDescriptions.mapIndexed { i, descr ->
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

        constellationLayerVm = createConstellationLayer(width, height)
        layers.add(constellationLayerVm)
    }

    private fun onGravityVectorChanged(gravity: Vector) {
        for (layer in layers) {
            layer.gravity = gravity
            layer.updateState()
        }

        notifyObservers()
    }

    private fun onConstellationTransitionChanged(sunSign: SunSign, shift: Float) {
        constellationLayerVm.translate(sunSign, shift)

        notifyObservers()
    }

    private fun onScatteringChanged(scattering: Float) {
        constellationLayerVm.scattering = scattering

        notifyObservers()
    }

    private fun notifyObservers() {
        for (observer in observers) {
            observer.onStateChanged()
        }
    }

    private fun createConstellationLayer(width: Int, height: Int) = ConstellationLayerViewModel(
        context = context,
        width = width,
        height = height,
        a = 18f
    )
}

private data class StarLayerDescription(
    val factor: Float,
    val maxStars: Int,
    val starsDistribution: List<Float>
)

