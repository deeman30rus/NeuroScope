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
import com.theroom101.core.android.BaseActivity
import com.theroom101.core.android.dpF
import com.theroom101.core.log.DebugLog
import com.theroom101.core.math.arc
import com.theroom101.neuroscopekit.R
import com.theroom101.ui.sunsigncarousel.SunSignCarousel

class ArcActivity: BaseActivity() {

    private val arcView: ArcView by lazy { findViewById(R.id.arc_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.kit_a_arc)
        val varSource = findViewById<EditText>(R.id.radius_source).also {
            it.addTextChangedListener(object: TextWatcher {

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

                override fun afterTextChanged(s: Editable) {
                    val v = s.toString().toInt()

                    arcView.radius = v
                }
            })
        }

        val v = varSource.text.toString().toInt()

        arcView.radius = v
    }
}

private val logger = DebugLog.default

internal class ArcView @JvmOverloads constructor(
        ctx: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
): View(ctx, attrs, defStyleAttr) {

    var radius = 100
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

    private val arcPaint = Paint().apply {
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

        for (i in bias .. (width - bias) step 10) {
            val x = i
            val a = arc(x, width / 2, radius)
            val y = height - arc(x, width / 2, radius).toFloat()

            canvas.drawCircle(x.toFloat(), y, dpF(3), arcPaint)
        }
    }
}