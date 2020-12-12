package com.theroom101.ui.parallax.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.theroom101.core.log.DebugLog
import com.theroom101.core.physics.Vector
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

private val logger = DebugLog.default

internal class Gravitometer(
    context: Context,
    private val tickRate: Int
) : SensorEventListener {

    private val sensorManager = context.sensorManager

    private val sensor = sensorManager.gravitySensor

    private val readings = GravityReadings(1000f / tickRate)

    private val readingsFlow = channelFlow {

        val period = (1000f / tickRate).toLong()
        var started = true

        while(started) {
            offer(readings.value())
            logger.log { "offered ${readings.value().x} ${readings.value().y}" }
            readings.extrapolate()

            delay(period)
        }

        awaitClose { started = false }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { e ->
            val g = Vector(-e.gravityX, e.gravityY)

            logger.log { "sensor changed ${g.x} ${g.y}" }

            readings.correct(g)
        }
    }

    fun prepare() {
        registerListener()
    }

    fun release() {
        unregisterListener()
    }

    fun readings() = readingsFlow.flowOn(Dispatchers.Default)

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
