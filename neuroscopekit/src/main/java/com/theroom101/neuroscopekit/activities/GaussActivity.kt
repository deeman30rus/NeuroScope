package com.theroom101.neuroscopekit.activities

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.theroom101.core.android.BaseActivity
import com.theroom101.core.android.dpF
import com.theroom101.core.log.DebugLog
import com.theroom101.core.math.gaussProb
import com.theroom101.neuroscopekit.R
import com.theroom101.ui.sunsigncarousel.SunSignCarousel

class GaussActivity : BaseActivity() {

    private val distributionView: DistributionView by lazy { findViewById(R.id.distrib_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.kit_a_gauss)

        val varSource = findViewById<EditText>(R.id.var_source).also {
            it.addTextChangedListener(object: TextWatcher {

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

                override fun afterTextChanged(s: Editable) {
                    val v = s.toString().toFloat()

                    distributionView.variance = v
                }
            })
        }

        val v = varSource.text.toString().toFloat()

        distributionView.variance = v
    }
}

private val logger = DebugLog.default

internal class DistributionView @JvmOverloads constructor(
        ctx: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
): View(ctx, attrs, defStyleAttr) {

    var variance = 100f
        set(value) {
            if (field == value) return

            field = value
            invalidate()
        }

    private val line15Paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = dpF(1)
    }

    private val line25Paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = dpF(1)
    }

    private val lineVhPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = dpF(1)
    }

    private val distribPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawARGB(255, 160, 248, 246) // bg

        canvas.drawLine(0f, height * 0.85f, width.toFloat(), height * 0.85f, line15Paint)

        canvas.drawLine(0f, height * 0.75f, width.toFloat(), height * 0.75f, line25Paint)

        for (i in 1 until SunSignCarousel.VISIBLE_ITEMS_AMOUNT) {
            val x = SunSignCarousel.ITEM_WIDTH.toFloat() * i
            canvas.drawLine(x, 0f, x, height.toFloat(), lineVhPaint)
        }

        val bias = (width % 10) / 2

        logger.log { "gauss $bias ${width - bias} ${width / 2f} $variance" }

        for (i in bias .. (width - bias) step 10) {
            val x = i.toFloat()
            val y = height * (1f - gaussProb(x, width / 2f, variance).toFloat())

            logger.log { "x = $x y = $y p = ${gaussProb(x, width / 2f, variance).toFloat()}" }

            canvas.drawCircle(x, y, dpF(3), distribPaint)
        }
    }
}