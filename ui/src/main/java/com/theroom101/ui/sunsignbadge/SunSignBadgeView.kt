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
import com.theroom101.core.log.DebugLog
import com.theroom101.ui.R

private val nameTextSize = spF(20)
private val infoTextSize = spF(14)

private val logger = DebugLog.default

/**
 * View to show sun sign name and shor info, support sunsign change animation
 */
class SunSignBadgeView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val leftPadding = dp(2)
    private val rightPadding = dp(2)

    private val topPadding = dp(6)
    private val textMargin = dp(6)
    private val botPadding = dp(6)

    private val gilroy = ResourcesCompat.getFont(context, R.font.gilroy_medium)

    private val namePaint = Paint().apply {
        typeface = gilroy
        color = resources.getColor(R.color.ui_sunsign_badge_name_color)
        style = Paint.Style.FILL_AND_STROKE
        textSize = nameTextSize
    }

    private val infoPaint = Paint().apply {
        typeface = gilroy
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

    var sunSign: SunSign = SunSign.Sagittarius

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMs = MeasureSpec.makeMeasureSpec(bounds.width(), MeasureSpec.AT_MOST)
        val heightMs = MeasureSpec.makeMeasureSpec(bounds.height(), MeasureSpec.AT_MOST)

        super.onMeasure(widthMs, heightMs)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val name = resources.getSunSignName(sunSign)
        val info = resources.getSunSignInfo(sunSign)

        drawName(canvas, name)
        drawInfo(canvas, info)
    }

    private fun calcWidth(): Int {
        return SunSign.values().maxOf {
            infoPaint.measureText(resources.getSunSignInfo(it))
        }.toInt() + leftPadding + rightPadding
    }

    private fun drawName(canvas: Canvas, name: String) {
        val y = topPadding.toFloat() + infoTextSize + textMargin + nameTextSize

        drawText(canvas, name, y, namePaint)
    }

    private fun drawInfo(canvas: Canvas, info: String) {
        val y = topPadding.toFloat() + infoTextSize

        drawText(canvas, info, y, infoPaint)
    }

    private fun drawText(canvas: Canvas, text: String, y: Float, paint: Paint) {
        val textWidth = paint.measureText(text)

        val x = (bounds.width() - textWidth) / 2

        canvas.drawText(text, x, y, paint)
    }

    private fun Resources.getSunSignName(sunSign: SunSign) = getString(when (sunSign) {
        SunSign.Capricorn -> {
            R.string.ui_carousel_capricorn_badge
        }
        SunSign.Aquarius -> {
            R.string.ui_carousel_aquarius_badge
        }
        SunSign.Pisces -> {
            R.string.ui_carousel_pisces_badge
        }
        SunSign.Aries -> {
            R.string.ui_carousel_aries_badge
        }
        SunSign.Taurus -> {
            R.string.ui_carousel_taurus_badge
        }
        SunSign.Gemini -> {
            R.string.ui_carousel_gemini_badge
        }
        SunSign.Cancer -> {
            R.string.ui_carousel_cancer_badge
        }
        SunSign.Leo -> {
            R.string.ui_carousel_leo_badge
        }
        SunSign.Virgo -> {
            R.string.ui_carousel_virgo_badge
        }
        SunSign.Libra -> {
            R.string.ui_carousel_libra_badge
        }
        SunSign.Scorpio -> {
            R.string.ui_carousel_scorpio_badge
        }
        SunSign.Ophiuchus -> {
            R.string.ui_carousel_ophiuchus_badge
        }
        SunSign.Sagittarius -> {
            R.string.ui_carousel_sagittarius_badge
        }
    })

    private fun Resources.getSunSignInfo(sunSign: SunSign) = getString(when (sunSign) {
        SunSign.Capricorn -> {
            R.string.ui_carousel_capricorn_info
        }
        SunSign.Aquarius -> {
            R.string.ui_carousel_aquarius_info
        }
        SunSign.Pisces -> {
            R.string.ui_carousel_pisces_info
        }
        SunSign.Aries -> {
            R.string.ui_carousel_aries_info
        }
        SunSign.Taurus -> {
            R.string.ui_carousel_taurus_info
        }
        SunSign.Gemini -> {
            R.string.ui_carousel_gemini_info
        }
        SunSign.Cancer -> {
            R.string.ui_carousel_cancer_info
        }
        SunSign.Leo -> {
            R.string.ui_carousel_leo_info
        }
        SunSign.Virgo -> {
            R.string.ui_carousel_virgo_info
        }
        SunSign.Libra -> {
            R.string.ui_carousel_libra_info
        }
        SunSign.Scorpio -> {
            R.string.ui_carousel_scorpio_info
        }
        SunSign.Ophiuchus -> {
            R.string.ui_carousel_ophiuchus_info
        }
        SunSign.Sagittarius -> {
            R.string.ui_carousel_sagittarius_info
        }
    })

}