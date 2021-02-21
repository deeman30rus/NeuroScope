package com.theroom101.ui.zodiacview

import android.content.Context
import com.theroom101.ui.parallax.vm.Star

/**
 * Like math graph, where stars are vertices and edges is set of connected vertices (indices represented in [Constellation.stars] field)
 */
internal class Constellation private constructor(
    val stars: List<Star>,
    val edges: Set<Pair<Int, Int>>
) {

    val width: Int = calcWidth(stars)
    val height: Int = calcHeight(stars)

    class Description(
        val stars: List<Star.Description>,
        val edges: Set<Pair<Int, Int>>
    )

    class Factory(
        private val context: Context,
    ) {

        fun create(description: Description): Constellation {
            val stars = description.stars.map {
                Star(
                    drawable = it.type.getDrawable(context).mutate(),
                    coordinates = it.coordinates,
                    size = it.size.size(),
                    alpha = it.alpha,
                    active = it.active,
                    dim = it.dim
                )
            }

            return Constellation(stars, description.edges)
        }
    }
}

private fun List<Int>.calcRange(): Int {
    return maxOf { it } - minOf { it }
}

private fun calcWidth(stars: List<Star>): Int {
    return stars.map { it.x }.calcRange()
}

private fun calcHeight(stars: List<Star>): Int {
    return stars.map { it.y }.calcRange()
}
