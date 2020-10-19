package com.theroom101.ui.parallax

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.theroom101.core.physics.Vector

internal typealias OnGravityChanged = (Vector) -> Unit

internal class Gravitometer(
    context: Context,
    private val onGravityChanged: OnGravityChanged
): SensorEventListener {

    private val sensorManager = context.sensorManager

    private val sensor = sensorManager.gravitySensor

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { e ->
            val g = Vector(-e.gravityX, e.gravityY)

            onGravityChanged(g)
        }
    }

    fun startReadings() = registerListener()

    fun stopReadings() = unregisterListener()

    private fun registerListener() {
        sensorManager.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    private fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }
}

private val SensorEvent.gravityX: Float
    get() = this.values[0]

private val SensorEvent.gravityY: Float
    get() = this.values[1]

private val Context.sensorManager: SensorManager
    get() = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager

private val SensorManager.gravitySensor: Sensor
    get() = this.getDefaultSensor(Sensor.TYPE_GRAVITY)
