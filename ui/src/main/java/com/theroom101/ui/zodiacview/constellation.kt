package com.theroom101.ui.zodiacview

import android.content.Context
import android.graphics.Point
import com.theroom101.core.android.dp
import com.theroom101.core.domain.SunSign
import com.theroom101.ui.parallax.vm.Star
import com.theroom101.ui.utils.starDrawable
import kotlin.random.Random
import kotlin.random.nextInt

private fun List<Int>.calcRange(): Int {
    return maxOf { it } - minOf { it }
}

private fun calcWidth(stars: List<Star>): Int {
    return stars.map { it.x }.calcRange()
}

private fun calcHeight(stars: List<Star>): Int {
    return stars.map { it.y }.calcRange()
}

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
        val stars: List<Pair<Int, Int>>,
        val edges: Set<Pair<Int, Int>>
    )

    class Factory(
        private val context: Context,
    ) {

        fun create(description: Description): Constellation {
            val stars = description.stars.map {
                Star(
                    drawable = context.starDrawable(0),
                    coordinates = Point(it.first, it.second),
                    size = Random.nextInt(dp(13) .. dp(16)),
                    alpha = 1f,
                    active = false,
                    dim = false
                )
            }

            return Constellation(stars, description.edges)
        }
    }
}

internal class ConstellationHolder(
    context: Context
) {
    private val factory = Constellation.Factory(context)

    private val constellations = mutableMapOf<SunSign, Constellation>()

    operator fun get(sunSign: SunSign): Constellation {
        val description = descriptions[sunSign] ?: error("Unknown constellation for $sunSign")
        return constellations[sunSign] ?: factory.create(description).also {
            constellations[sunSign] = it
        }
    }
}

private val capricorn = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val aquarius = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)
private val pisces = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val aries = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val taurus = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val gemini = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)
private val cancer = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val leo = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val virgo = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val libra = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val scorpio = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val ophiuchus = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val sagittarius = Constellation.Description(
    stars = listOf(
        0 to 0,
        100 to 0,
        100 to 100,
        0 to 100
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val descriptions = mapOf(
    SunSign.Capricorn to capricorn,
    SunSign.Aquarius to aquarius,
    SunSign.Pisces to pisces,
    SunSign.Aries to aries,
    SunSign.Taurus to taurus,
    SunSign.Gemini to gemini,
    SunSign.Cancer to cancer,
    SunSign.Leo to leo,
    SunSign.Virgo to virgo,
    SunSign.Libra to libra,
    SunSign.Scorpio to scorpio,
    SunSign.Ophiuchus to ophiuchus,
    SunSign.Sagittarius to sagittarius,
)