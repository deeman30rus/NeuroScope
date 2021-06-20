package com.delizarov.forecast.ui.nightsky.parallax

import android.content.Context
import android.view.Choreographer
import com.delizarov.forecast.ui.nightsky.sensor.Gravitometer
import com.theroom101.core.math.Vector
import kotlin.math.min

typealias OnGravityChanged = (Vector) -> Unit

/**
 * Gravity source model, to handle device rotation and gravity vector inertia
 * udpate rate as fast as fps
 */
class GravitySource(
    context: Context,
) {
    private val gravitometer = Gravitometer(context, ::updateGravity)
    private val choreographer: Choreographer by lazy { Choreographer.getInstance() }

    private val frameCallback = FrameCallback()

    private val g = Gravity()

    var onStateChangedCallback: OnGravityChanged? = null

    fun startUpdates() {
        gravitometer.prepare()
        choreographer.postFrameCallback(frameCallback)
    }

    fun stopUpdates() {
        gravitometer.release()
        choreographer.removeFrameCallback(frameCallback)
    }

    private fun updateGravity(gravity: Vector) {
        g.updateSourceVector(gravity)
    }

    class Gravity {
        var acceleration = Vector()
            private set

        private var sensorGravity = Vector()
        private var progress = Progress()

        private var interpolator = Interpolator(Vector(), Vector())

        fun updateSourceVector(newGravityVector: Vector) {
            interpolator = Interpolator(acceleration, newGravityVector)

            sensorGravity = newGravityVector

            progress.renew()
        }

        fun updateInertia() {
            if (acceleration == sensorGravity) return

            acceleration = interpolator.value(progress.value)
            progress.inc()
        }
    }

    private inner class FrameCallback: Choreographer.FrameCallback {

        override fun doFrame(frameTimeNanos: Long) {
            choreographer.postFrameCallback(frameCallback)

            g.updateInertia()

            onStateChangedCallback?.invoke(g.acceleration)
        }
    }
}

private class Progress {

    var value = 0f
        private set

    fun renew() {
        value = 0f
    }

    fun inc() {
        value = min(1f, value + 1f / 30)
    }
}

private class Interpolator(
    private val start: Vector,
    private val end: Vector
) {

    /**
     * calculates intermediate value between [start] and [end] with given progress
     * @param progress float value in range 0 .. 1
     */
    fun value(progress: Float): Vector {
        return start + (end - start) * progress
    }
}
