package com.delizarov.forecast.ui.nightsky

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.theroom101.core.assertions.Asserts

/**
 * Android [View] to render parallax layers of stars and constellations
 */
class NightSkyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr), NightSkyViewModel.Observer {

    private val vm = NightSkyViewModel(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        Asserts.assertFalse(vm.isBound) { "Probably forget to unbound view or multiple rebindings" }
        if (vm.isBound) {
            vm.unbind(this)
        }

        vm.bind(this, w, h)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        vm.unbind(this)
    }

    override fun onStateChanged() {
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (layerVm in vm.layers) {
            layerVm.drawOnCanvas(canvas)
        }
    }
}