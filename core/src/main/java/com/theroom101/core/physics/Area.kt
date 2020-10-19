package com.theroom101.core.physics

import android.graphics.Point
import android.graphics.Rect
import kotlin.random.Random

typealias Area = Rect

fun createArea(center: Point, width: Int, height: Int) = Rect(
    center.x - width / 2,
    center.y - height / 2,
    center.x + width / 2,
    center.y + height / 2
)

fun Area.randomPoint() = Point(
    Random.nextInt(left, right),
    Random.nextInt(top, bottom)
)