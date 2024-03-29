package com.theroom101.ui.sunsignbadge

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.theroom101.core.android.dp
import com.theroom101.core.android.spF
import com.theroom101.core.domain.SunSign
import com.theroom101.core.math.floor
import com.theroom101.ui.R
import kotlin.math.abs
import kotlin.math.sign

private val nameTextSize = spF(20)
private val infoTextSize = spF(14)

/**
 * View to show sun sign name and short info, support sunsign change animation
 */
class SunSignBadgeView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private sealed class State {
        class Idle(val sunSign: SunSign) : State()

        class Transition(
            val from: SunSign,
            val to: SunSign,
            val progress: Float
        ): State()
    }

    private val renderer = Renderer()

    private val leftPadding = dp(2)
    private val rightPadding = dp(2)

    private val topPadding = dp(6)
    private val textMargin = dp(6)
    private val botPadding = dp(6)
    private val gilroyMed = ResourcesCompat.getFont(context, R.font.gilroy_medium)
    private val gilroyBold = ResourcesCompat.getFont(context, R.font.gilroy_bold)

    private val namePaint = Paint().apply {
        typeface = gilroyBold
        color = resources.getColor(R.color.ui_sunsign_badge_name_color)
        style = Paint.Style.FILL_AND_STROKE
        textSize = nameTextSize
    }

    private val infoPaint = Paint().apply {
        typeface = gilroyMed
        color = resources.getColor(R.color.ui_sunsign_badge_info_color)
        style = Paint.Style.FILL_AND_STROKE
        textSize = infoTextSize
    }

    private var bounds = Rect(
            0,
            0,
            calcWidth(),
            topPadding + infoTextSize.toInt() + textMargin + nameTextSize.toInt() + botPadding
    )

    fun translate(from: SunSign, to: SunSign, progress: Float) {
        state = State.Transition(from, to, progress)
    }

    private var state: State = State.Idle(SunSign.Sagittarius)
        set(value) {
            if (field == value) return

            field = value
            invalidate()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMs = MeasureSpec.makeMeasureSpec(bounds.width(), MeasureSpec.AT_MOST)
        val heightMs = MeasureSpec.makeMeasureSpec(bounds.height(), MeasureSpec.AT_MOST)

        super.onMeasure(widthMs, heightMs)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        renderer.render(canvas, state)
    }

    private fun calcWidth(): Int {
        return SunSign.values().maxOf {
            infoPaint.measureText(resources.getSunSignInfo(it))
        }.toInt() + leftPadding + rightPadding
    }

    private inner class Renderer {

        private val progressThreshold = 0.75f // in percents
        private val multiplier = 1f / progressThreshold

        private val nameShift = nameTextSize
        private val infoShift = topPadding.toFloat() + infoTextSize

        private val infoBaseY = topPadding.toFloat() + infoTextSize
        private val nameBaseY = topPadding.toFloat() + infoTextSize + textMargin + nameTextSize

        fun render(canvas: Canvas, state: State) = when(state) {
            is State.Idle -> renderIdleState(canvas, state.sunSign)
            is State.Transition -> renderTransitionState(canvas, state.from, state.to, state.progress)
        }

        private fun renderIdleState(canvas: Canvas, sunSign: SunSign) {
            val name = resources.getSunSignName(sunSign)
            val info = resources.getSunSignInfo(sunSign)

            val nameY = topPadding.toFloat() + infoTextSize + textMargin + nameTextSize
            val infoY = topPadding.toFloat() + infoTextSize

            infoPaint.alpha = 128
            namePaint.alpha = 255

            drawText(canvas, name, nameY, namePaint)
            drawText(canvas, info, infoY, infoPaint)
        }

        private fun renderTransitionState(canvas: Canvas, from: SunSign, to: SunSign, progress: Float) {
            drawFrom(canvas, from, progress)
            drawTo(canvas, to, progress)
        }

        private fun drawFrom(canvas: Canvas, fromSign: SunSign, progress: Float) {
            if (abs(progress) >= progressThreshold) return

            val name = resources.getSunSignName(fromSign)
            val info = resources.getSunSignInfo(fromSign)

            val k = progress * multiplier

            val infoY = infoBaseY + infoShift * k
            val nameY = nameBaseY + nameShift * k

            val alpha = floor((1f - abs(progress) * multiplier) * 255)

            infoPaint.alpha = alpha / 2
            namePaint.alpha = alpha

            drawText(canvas, name, nameY, namePaint)
            drawText(canvas, info, infoY, infoPaint)
        }

        private fun drawTo(canvas: Canvas, sunSign: SunSign, progress: Float) {
            if (abs(progress) < 1 - progressThreshold) return

            val bias = abs(progress) - (1 - progressThreshold)

            val name = resources.getSunSignName(sunSign)
            val info = resources.getSunSignInfo(sunSign)

            val k = (1 - bias * multiplier)

            val infoY = infoBaseY - sign(progress) * infoShift * k
            val nameY = nameBaseY - sign(progress) * nameShift * k

            val alpha = floor((abs(bias) * multiplier) * 255)

            infoPaint.alpha = alpha / 2
            namePaint.alpha = alpha

            drawText(canvas, name, nameY, namePaint)
            drawText(canvas, info, infoY, infoPaint)
        }

        private fun drawText(canvas: Canvas, text: String, y: Float, paint: Paint) {
            val textWidth = paint.measureText(text)

            val x = (bounds.width() - textWidth) / 2

            canvas.drawText(text, x, y, paint)
        }

    }
}

private fun Resources.getSunSignName(sunSign: SunSign) = getString(when (sunSign) {
    SunSign.Capricorn -> R.string.ui_carousel_capricorn_badge
    SunSign.Aquarius -> R.string.ui_carousel_aquarius_badge
    SunSign.Pisces -> R.string.ui_carousel_pisces_badge
    SunSign.Aries -> R.string.ui_carousel_aries_badge
    SunSign.Taurus -> R.string.ui_carousel_taurus_badge
    SunSign.Gemini -> R.string.ui_carousel_gemini_badge
    SunSign.Cancer -> R.string.ui_carousel_cancer_badge
    SunSign.Leo -> R.string.ui_carousel_leo_badge
    SunSign.Virgo -> R.string.ui_carousel_virgo_badge
    SunSign.Libra -> R.string.ui_carousel_libra_badge
    SunSign.Scorpio -> R.string.ui_carousel_scorpio_badge
    SunSign.Ophiuchus -> R.string.ui_carousel_ophiuchus_badge
    SunSign.Sagittarius -> R.string.ui_carousel_sagittarius_badge
})

private fun Resources.getSunSignInfo(sunSign: SunSign) = getString(when (sunSign) {
    SunSign.Capricorn -> R.string.ui_carousel_capricorn_info
    SunSign.Aquarius -> R.string.ui_carousel_aquarius_info
    SunSign.Pisces -> R.string.ui_carousel_pisces_info
    SunSign.Aries -> R.string.ui_carousel_aries_info
    SunSign.Taurus -> R.string.ui_carousel_taurus_info
    SunSign.Gemini -> R.string.ui_carousel_gemini_info
    SunSign.Cancer -> R.string.ui_carousel_cancer_info
    SunSign.Leo -> R.string.ui_carousel_leo_info
    SunSign.Virgo -> R.string.ui_carousel_virgo_info
    SunSign.Libra -> R.string.ui_carousel_libra_info
    SunSign.Scorpio -> R.string.ui_carousel_scorpio_info
    SunSign.Ophiuchus -> R.string.ui_carousel_ophiuchus_info
    SunSign.Sagittarius -> R.string.ui_carousel_sagittarius_info
})