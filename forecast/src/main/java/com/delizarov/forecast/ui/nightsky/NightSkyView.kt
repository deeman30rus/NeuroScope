package com.delizarov.forecast.ui.nightsky

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.theroom101.core.assertions.Asserts
import com.theroom101.core.log.DebugLog

/**
 * Android [View] to render parallax layers of stars and constellations
 */
class NightSkyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), NightSkyViewModel.Observer {

    private var viewModel: NightSkyViewModel? = null

    fun bind(vm: NightSkyViewModel) {
        viewModel = vm
        vm.observe(this)
    }

    fun unbindVm() {
        viewModel?.removeObserver(this)
        viewModel = null
    }

    override fun onStateChanged() {
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        viewModel?.let { vm ->
            for (layerVm in vm.layers) {
                layerVm.drawOnCanvas(canvas)
            }
        }
    }
}