package com.theroom101.ui.parallax

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View
import androidx.core.content.ContextCompat
import com.theroom101.core.math.Vector
import com.theroom101.core.math.createArea
import com.theroom101.core.math.randomPoint
import com.theroom101.ui.R
import com.theroom101.ui.parallax.sensor.Gravitometer
import com.theroom101.ui.parallax.vm.LayerViewModel
import com.theroom101.ui.parallax.vm.Star
import com.theroom101.ui.utils.StarSize
import com.theroom101.ui.utils.StarType
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.plus
import kotlin.random.Random

private const val LAYER_1_AREA = 1.08f
private const val LAYER_2_AREA = 1.16f
private const val LAYER_3_AREA = 1.24f
private const val LAYER_4_AREA = 1.32f

private val layerDescriptions = listOf(
    Layer.Description(LAYER_1_AREA, 18, listOf(0.15f, 0.15f, 0.7f)),
    Layer.Description(LAYER_2_AREA, 18, listOf(0.2f, 0.2f, 0.6f)),
    Layer.Description(LAYER_3_AREA, 18, listOf(0.4f, 0.4f, 0.2f)),
    Layer.Description(LAYER_4_AREA, 18, listOf(0.45f, 0.45f, 0.1f)),
)

class ParallaxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private val gravitometer = Gravitometer(context, ::updateGravity)
    private val scope = MainScope() + SupervisorJob()

    private val choreographer: Choreographer by lazy { Choreographer.getInstance() }

    private val frameRequester = FrameHandler()

    private var initialized = false

    private val layers = mutableListOf<Layer>()

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
        layers.forEach { it.layerVM.gravity = gravity }
    }

    private fun initialize(w: Int, h: Int) {
        choreographer.postFrameCallback(frameRequester)

        for ((i, description) in layerDescriptions.withIndex()) {
            layers.add(
                Layer.create(i, context, w, h, description)
            )
        }

        initialized = true
    }

    private inner class FrameHandler : Choreographer.FrameCallback {

        override fun doFrame(frameTimeNanos: Long) {
            choreographer.postFrameCallback(frameRequester)

            layers.forEach { layer ->
                layer.layerVM.update()
            }

            invalidate()
        }
    }
}

private fun Canvas.draw(layers: List<Layer>) = layers.forEach { it.drawOn(this) }

private class Layer private constructor(
    val layerVM: LayerViewModel
) {

    fun drawOn(canvas: Canvas) {
        layerVM.stars.forEach { star ->
            val alpha = (255 * star.alpha).toInt()

            star.drawable.setBounds(
                star.x + layerVM.dx,
                star.y + layerVM.dy,
                star.x + layerVM.dx + star.size,
                star.y + layerVM.dy + star.size
            )
            star.drawable.alpha = alpha

            star.drawable.draw(canvas)
        }
    }

    class Description(
        val factor: Float,
        val maxStars: Int,
        val starsDistribution: List<Float>
    )

    companion object {

        fun create(
            layerNo: Int,
            context: Context,
            width: Int,
            height: Int,
            description: Description
        ): Layer {

            val center = Point(width / 2, height / 2)

            val layerWidth = (width * (description.factor)).toInt()
            val layerHeight = (height * (description.factor)).toInt()

            val layerArea = createArea(center, layerWidth, layerHeight)

            val deltaMax = (width * (description.factor - 1)) / 2

            val starsVm = LayerViewModel(
                a = deltaMax / 5.5f
            ) {
                (1..description.maxStars).map {
                    val r = Random.nextFloat()
                    val type = StarType.randomInDistribution(r, description.starsDistribution)

                    Star(
                        drawable = type.getDrawable(context).mutate(),
                        coordinates = layerArea.randomPoint(),
                        size = StarSize.fromInt(layerNo).size(),
                        alpha = Random.nextFloat(),
                        active = Random.nextBoolean(),
                        dim = Random.nextBoolean()
                    )
                }
            }

            return Layer(starsVm)
        }
    }
}


