package com.theroom101.ui.sunsigncarousel

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.RectF
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.core.android.dpF
import com.theroom101.ui.R

internal class CarouselMainItemDecoration(
        context: Context
): RecyclerView.ItemDecoration() {

    private val angles = listOf(
            -10f,
            80f,
            170f,
            260f,
    )

    private val outerRadius = dpF(40)
    private val innerRadius = dpF(36)


    private val resources = context.resources

    private val paintOuter = Paint().apply {
        color = resources.getColor(R.color.core_white25)
        style = Paint.Style.STROKE
        strokeWidth = dpF(1)
        isAntiAlias = true
    }

    private val paintInner = Paint().apply {
        color = resources.getColor(R.color.core_white25)
        pathEffect = DashPathEffect(floatArrayOf(dpF(4), dpF(2)), 0f)
        style = Paint.Style.STROKE
        strokeWidth = dpF(1)
        isAntiAlias = true
    }

    private val rect = RectF()

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val cx = c.width / 2
        val cy = c.height / 2

        with(rect) {
            top = cy - outerRadius
            bottom = cy + outerRadius
            left = cx - outerRadius
            right = cx + outerRadius
        }

        c.drawCircle(cx.toFloat(), cy.toFloat(), innerRadius, paintInner)

        for (startAngle in angles) {
            c.drawArc(rect, startAngle, 20f, false, paintOuter)
        }
    }
}


class CarouselAlphaDecoration: RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)


    }


}

