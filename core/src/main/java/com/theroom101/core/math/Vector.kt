package com.theroom101.core.math

import android.graphics.Point

data class Vector(
    var x: Float = 0f,
    var y: Float = 0f
) {
    operator fun times(k: Float) = Vector(this.x * k, this.y * k)

    operator fun times(k: Int) = Vector(this.x * k, this.y * k)

    operator fun plus(other: Vector) = Vector(this.x + other.x, this.y + other.y)

    operator fun minus(other: Vector) = Vector(this.x - other.x, this.y - other.y)
}

operator fun Point.plus(vector: Vector) = Point(
    (x + vector.x).toInt(),
    (y + vector.y).toInt()
)

operator fun Point.plusAssign(vector: Vector) {
    x = (x + vector.x).toInt()
    y = (y + vector.y).toInt()
}
