package com.theroom101.ui.parallax

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View
import androidx.core.content.ContextCompat
import com.theroom101.core.math.Vector
import com.theroom101.ui.R
import com.theroom101.ui.models.StarDrawableFactory
import com.theroom101.ui.models.StarModel
import com.theroom101.ui.parallax.sensor.Gravitometer
import com.theroom101.ui.parallax.vm.LayerViewModel
import com.theroom101.ui.parallax.vm.layerViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.plus

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

class ParallaxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private val gravitometer = Gravitometer(context, ::updateGravity)
    private val scope = MainScope() + SupervisorJob()

    private val starDrawableFactory = StarDrawableFactory(resources)

    private val choreographer: Choreographer by lazy { Choreographer.getInstance() }

    private val frameRequester = FrameHandler()

    private var initialized = false

    private val layers = mutableListOf<LayerViewModel>()
    private val starDrawables = mutableMapOf<StarModel, Drawable>()

    init {
        background = ContextCompat.getDrawable(context, R.drawable.ui_parallax_view_bg)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        initialize(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.draw(layers)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        gravitometer.prepare()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        gravitometer.release()
        scope.coroutineContext.cancelChildren()

        initialized = false
    }

    private fun updateGravity(gravity: Vector) {
        layers.forEach { it.gravity = gravity }
    }

    private fun initialize(w: Int, h: Int) {
        choreographer.postFrameCallback(frameRequester)

        layers.clear()
        starDrawables.clear()

        for ((i, description) in layerDescriptions.withIndex()) {
            val (factor, starsCount, distribution) = description

            layers.add( layerViewModel {
                    this.layerNo = i
                    this.context = this@ParallaxView.context
                    this.viewWidth = w
                    this.viewHeight = h
                    this.starsCount = starsCount
                    this.factor = factor
                    this.starsDistribution = distribution
                }
            )

            starDrawables.putAll(
                layers.flatMap { it.stars }.map { it to starDrawableFactory.createStarDrawable(it) }
            )
        }

        initialized = true
    }

    private fun Canvas.draw(layers: List<LayerViewModel>) = layers.forEach { it.drawOn(this) }

    private fun LayerViewModel.drawOn(canvas: Canvas) {
        for (star in stars) {
            val drawable = starDrawables[star] ?: error("Model to drawable mapping corrupted")

            val alpha = (255 * star.alpha).toInt()

            drawable.setBounds(
                star.x + dx,
                star.y + dy,
                star.x + dx + star.size,
                star.y + dy + star.size
            )
            drawable.alpha = alpha

            drawable.draw(canvas)
        }
    }

    private inner class FrameHandler : Choreographer.FrameCallback {

        override fun doFrame(frameTimeNanos: Long) {
            choreographer.postFrameCallback(frameRequester)

            layers.forEach { vm ->
                vm.update()
            }

            invalidate()
        }
    }
}

private data class LayerDescription(
    val factor: Float,
    val maxStars: Int,
    val starsDistribution: List<Float>
)


