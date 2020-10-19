package com.theroom101.ui.parallax

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View
import androidx.core.content.ContextCompat
import com.theroom101.core.android.dp
import com.theroom101.core.physics.Vector
import com.theroom101.core.physics.createArea
import com.theroom101.core.physics.randomPoint
import com.theroom101.ui.R
import com.theroom101.ui.parallax.vm.LayerViewModel
import com.theroom101.ui.parallax.vm.Star
import kotlin.random.Random
import kotlin.random.nextInt

private const val G = 9.81f

private const val LAYER_1_AREA = 1.08f
private const val LAYER_2_AREA = 1.16f
private const val LAYER_3_AREA = 1.32f
private const val LAYER_4_AREA = 1.64f

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

        gravitometer.startReadings()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        gravitometer.stopReadings()
        initialized = false
    }

    private fun updateGravity(gravity: Vector) {
        layers.forEach { it.layerVM.gravity = gravity }
    }

    private fun initialize(w: Int, h: Int) {
        choreographer.postFrameCallback(frameRequester)

        for (description in layerDescriptions) {
            layers.add(
                Layer.create(context, w, h, description)
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
            context: Context,
            width: Int,
            height: Int,
            description: Description
        ): Layer {
            fun starType(v: Float, distribution: List<Float>): Int {
                var sum = 0f
                for (i in distribution.indices) {
                    sum += distribution[i]

                    if (v <= sum) return i
                }

                error("bad distribution")
            }

            val center = Point(width / 2, height / 2)

            val layerWidth = (width * (description.factor)).toInt()
            val layerHeight = (height * (description.factor)).toInt()

            val layerArea = createArea(center, layerWidth, layerHeight)

            val deltaMax = (width * (description.factor - 1))

            val starsVm = LayerViewModel(
                a = deltaMax / G
            ) {
                (1..description.maxStars).map {
                    val r = Random.nextFloat()
                    val type = starType(r, description.starsDistribution)

                    Star(
                        drawable = context.starDrawable(type).mutate(),
                        coordinates = layerArea.randomPoint(),
                        size = type.starSize,
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

private fun Context.starDrawable(type: Int): Drawable = when (type) {
    0 -> ContextCompat.getDrawable(this, R.drawable.ui_star_1)
        ?: error("star 1 drawable resource not found")
    1 -> ContextCompat.getDrawable(this, R.drawable.ui_star_2)
        ?: error("star 2 drawable resource not found")
    2 -> ContextCompat.getDrawable(this, R.drawable.ui_star_3)
        ?: error("star 3 drawable resource not found")
    else -> error("Unknown start type $type")
}

private val Int.starSize: Int
    get() = when (this) {
        0 -> Random.nextInt(dp(8)..dp(15))
        1 -> Random.nextInt(dp(6)..dp(12))
        2 -> Random.nextInt(dp(4), dp(8))
        else -> error("Unknown star type $this")
    }