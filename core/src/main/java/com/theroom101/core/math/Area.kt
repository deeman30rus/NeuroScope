package com.theroom101.core.math

import android.graphics.Point
import android.graphics.Rect
import kotlin.random.Random

typealias Area = Rect

operator fun Area.contains(point: Point) = point.x in (left .. right) && point.y in (top .. bottom)

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