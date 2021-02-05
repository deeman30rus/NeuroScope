package com.theroom101.ui.sunsigncarousel

import android.content.Context
import android.graphics.*
import android.graphics.drawable.VectorDrawable
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.theroom101.core.android.dpF
import com.theroom101.core.math.arc
import com.theroom101.core.math.gaussProb
import com.theroom101.ui.R

/**
 * Highlights central item
 */
internal class CarouselMainItemDecoration(
        context: Context
) : RecyclerView.ItemDecoration() {

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

    var biasAngle: Float = 0f

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
            c.drawArc(rect, biasAngle + startAngle, 20f, false, paintOuter)
        }
    }
}

/**
 * Setting alpha to items according its position
 */
internal class CarouselAlphaDecoration : RecyclerView.ItemDecoration() {

    private val variance = 56000f

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        for (i in 0..parent.childCount) {
            val child = parent.getChildAt(i) as? ImageView ?: continue
            val drawable = child.drawable as VectorDrawable

            val p = calcAlphaForChild(child, (parent.width - SunSignCarousel.ITEM_WIDTH) / 2f)

            drawable.alpha = (p * 255).toInt()
        }
    }

    private fun calcAlphaForChild(child: View, cx: Float): Double {
        val p = gaussProb(child.left.toFloat(), cx, variance)

        return p
    }
}

internal class BottomOffsetDecoration : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val radius = (parent as SunSignCarousel).radius
        for (i in 0..parent.childCount) {
            val child = parent.getChildAt(i) as? ImageView ?: continue
            val midX = child.left + SunSignCarousel.ITEM_WIDTH / 2

            val bias = arc(midX, parent.width / 2, radius)

            child.setPadding(
                    child.paddingLeft,
                    child.paddingTop,
                    child.paddingRight,
                    bias
            )
        }
    }
}