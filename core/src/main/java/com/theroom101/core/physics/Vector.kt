package com.theroom101.core.physics

class Vector(
    var x: Float = 0f,
    var y: Float = 0f
) {
    operator fun times(k: Float) = Vector(this.x * k, this.y * k)

    operator fun times(k: Int) = Vector(this.x * k, this.y * k)

    operator fun plus(other: Vector) = Vector(this.x + other.x, this.y + other.y)

    operator fun minus(other: Vector) = Vector(this.x - other.x, this.y - other.y)
}