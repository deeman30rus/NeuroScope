package com.theroom101.ui.zodiacview

import com.theroom101.ui.models.StarModel

/**
 * Like math graph, where stars are vertices and edges is set of connected vertices (indices represented in [Constellation.stars] field)
 */
internal class Constellation(
    val stars: List<StarModel>,
    val edges: Set<Pair<Int, Int>>
) {

    val width: Int = calcWidth(stars)
    val height: Int = calcHeight(stars)
}

private fun List<Int>.calcRange(): Int {
    return maxOf { it } - minOf { it }
}

private fun calcWidth(stars: List<StarModel>): Int {
    return stars.map { it.x }.calcRange()
}

private fun calcHeight(stars: List<StarModel>): Int {
    return stars.map { it.y }.calcRange()
}
