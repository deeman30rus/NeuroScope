package com.theroom101.ui.zodiacview

import com.theroom101.core.domain.SunSign

internal class Star(
    val x: Int,
    val y: Int,
    val type: Int
)

/**
 * Like math graph, where stars are vertices and edges is set of connected vertices (indices represented in [Constellation.stars] field)
 */
internal class Constellation(
    val stars: List<Star>,
    val edges: Set<Pair<Int, Int>>
)

private val capricorn = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val aquarius = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)
private val pisces = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val aries = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val taurus = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val gemini = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)
private val cancer = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val leo = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val virgo = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val libra = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val scorpio = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val ophiuchus = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

private val sagittarius = Constellation(
    stars = listOf(
        Star(0, 0, 0),
        Star(100, 0, 0),
        Star(100, 100, 0),
        Star(0, 100, 0),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 0
    )
)

internal val sunSignConstellations = mapOf(
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
