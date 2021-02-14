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
                    size = Random.nextInt(dp(13)..dp(16)),
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
        dp(49) to dp(8),
        dp(51) to dp(16),
        dp(70) to dp(62),
        dp(71) to dp(69),
        dp(59) to dp(73),
        dp(42) to dp(82),
        dp(24) to dp(87),
        dp(12) to dp(90),
        dp(14) to dp(84),
        dp(24) to dp(72),
        dp(33) to dp(61),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        1 to 10,
        2 to 3,
        3 to 4,
        4 to 5,
        5 to 6,
        6 to 7,
        7 to 8,
        8 to 9,
        9 to 10
    )
)

private val aquarius = Constellation.Description(
    stars = listOf(
        dp(88) to dp(6),
        dp(59) to dp(26),
        dp(32) to dp(43),
        dp(56) to dp(52),
        dp(78) to dp(51),
        dp(31) to dp(54),
        dp(28) to dp(58),
        dp(23) to dp(63),
        dp(44) to dp(91),
        dp(49) to dp(78),
        dp(65) to dp(76),
        dp(70) to dp(78),
        dp(83) to dp(88),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        2 to 5,
        3 to 4,
        5 to 6,
        6 to 7,
        7 to 8,
        8 to 9,
        9 to 10,
        10 to 11,
        11 to 12
    )
)
private val pisces = Constellation.Description(
    stars = listOf(
        dp(14) to dp(90),
        dp(25) to dp(94),
        dp(30) to dp(84),
        dp(55) to dp(84),
        dp(72) to dp(86),
        dp(92) to dp(88),
        dp(80) to dp(80),
        dp(66) to dp(66),
        dp(61) to dp(58),
        dp(50) to dp(34),
        dp(48) to dp(22),
        dp(41) to dp(17),
        dp(43) to dp(10),
        dp(48) to dp(8),
        dp(58) to dp(9),
        dp(58) to dp(19),
        dp(54) to dp(24)
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 4,
        4 to 5,
        5 to 6,
        6 to 7,
        7 to 8,
        8 to 9,
        9 to 10,
        10 to 11,
        11 to 12,
        12 to 13,
        13 to 14,
        14 to 15,
        15 to 16,
        16 to 10
    )
)

private val aries = Constellation.Description(
    stars = listOf(
        dp(26) to dp(40),
        dp(57) to dp(46),
        dp(70) to dp(53),
        dp(72) to dp(59),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3
    )
)

private val taurus = Constellation.Description(
    stars = listOf(
        dp(18) to dp(26),
        dp(44) to dp(49),
        dp(49) to dp(52),
        dp(54) to dp(55),
        dp(64) to dp(64),
        dp(84) to dp(78),
        dp(88) to dp(82),
        dp(53) to dp(49),
        dp(50) to dp(43),
        dp(47) to dp(30),
        dp(32) to dp(10),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 4,
        3 to 7,
        4 to 5,
        5 to 6,
        7 to 8,
        8 to 9,
        9 to 10
    )
)

private val gemini = Constellation.Description(
    stars = listOf(
        dp(20) to dp(23),
        dp(26) to dp(23),
        dp(34) to dp(24),
        dp(61) to dp(38),
        dp(76) to dp(43),
        dp(82) to dp(41),
        dp(72) to dp(49),
        dp(66) to dp(61),
        dp(62) to dp(73),
        dp(42) to dp(52),
        dp(29) to dp(49),
        dp(13) to dp(42),
        dp(11) to dp(35),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 4,
        4 to 5,
        4 to 6,
        6 to 7,
        7 to 8,
        7 to 9,
        9 to 10,
        10 to 11,
        11 to 12,
        12 to 0
    )
)
private val cancer = Constellation.Description(
    stars = listOf(
        dp(31) to dp(17),
        dp(43) to dp(33),
        dp(46) to dp(40),
        dp(45) to dp(64),
        dp(78) to dp(54),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        2 to 4
    )
)

private val leo = Constellation.Description(
    stars = listOf(
        dp(57) to dp(14),
        dp(48) to dp(14),
        dp(48) to dp(31),
        dp(55) to dp(39),
        dp(65) to dp(37),
        dp(75) to dp(45),
        dp(41) to dp(75),
        dp(29) to dp(89),
        dp(32) to dp(64),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 4,
        3 to 8,
        4 to 5,
        5 to 6,
        6 to 7,
        7 to 8
    )
)

private val virgo = Constellation.Description(
    stars = listOf(
        dp(66) to dp(7),
        dp(65) to dp(25),
        dp(61) to dp(37),
        dp(63) to dp(56),
        dp(71) to dp(69),
        dp(58) to dp(87),
        dp(53) to dp(86),
        dp(46) to dp(98),
        dp(27) to dp(90),
        dp(38) to dp(70),
        dp(48) to dp(61),
        dp(49) to dp(40),
        dp(31) to dp(34),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        2 to 11,
        3 to 4,
        4 to 5,
        4 to 10,
        5 to 6,
        6 to 7,
        8 to 9,
        9 to 10,
        10 to 11,
        11 to 12
    )
)

private val libra = Constellation.Description(
    stars = listOf(
        dp(26) to dp(46),
        dp(35) to dp(41),
        dp(42) to dp(35),
        dp(51) to dp(16),
        dp(75) to dp(32),
        dp(71) to dp(66),
        dp(53) to dp(79),
        dp(52) to dp(85),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 4,
        3 to 5,
        4 to 5,
        5 to 6,
        6 to 7
    )
)

private val scorpio = Constellation.Description(
    stars = listOf(
        dp(16) to dp(63),
        dp(9) to dp(70),
        dp(7) to dp(76),
        dp(14) to dp(86),
        dp(30) to dp(87),
        dp(42) to dp(82),
        dp(46) to dp(70),
        dp(48) to dp(57),
        dp(61) to dp(38),
        dp(67) to dp(35),
        dp(72) to dp(31),
        dp(91) to dp(25),
        dp(87) to dp(15),
        dp(90) to dp(34),
        dp(89) to dp(45),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 4,
        4 to 5,
        5 to 6,
        6 to 7,
        7 to 8,
        8 to 9,
        9 to 10,
        10 to 11,
        11 to 12,
        11 to 13,
        13 to 14
    )
)

private val ophiuchus = Constellation.Description(
    stars = listOf(
        dp(2) to dp(25),
        dp(21) to dp(39),
        dp(32) to dp(52),
        dp(37) to dp(27),
        dp(40) to dp(24),
        dp(44) to dp(8),
        dp(54) to dp(3),
        dp(63) to dp(14),
        dp(76) to dp(30),
        dp(85) to dp(41),
        dp(97) to dp(24),
        dp(83) to dp(43),
        dp(73) to dp(55),
        dp(56) to dp(64),
        dp(43) to dp(64),
        dp(51) to dp(83),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        2 to 14,
        3 to 4,
        4 to 5,
        5 to 6,
        6 to 7,
        7 to 8,
        8 to 9,
        9 to 10,
        9 to 11,
        11 to 12,
        12 to 13,
        13 to 15,
        13 to 14
    )
)

private val sagittarius = Constellation.Description(
    stars = listOf(
        dp(21) to dp(7),
        dp(33) to dp(15),
        dp(37) to dp(15),
        dp(43) to dp(12),
        dp(46) to dp(29),
        dp(53) to dp(29),
        dp(43) to dp(37),
        dp(39) to dp(31),
        dp(19) to dp(28),
        dp(8) to dp(40),
        dp(5) to dp(43),
        dp(18) to dp(62),
        dp(26) to dp(74),
        dp(40) to dp(76),
        dp(37) to dp(67),
        dp(64) to dp(25),
        dp(70) to dp(10),
        dp(70) to dp(35),
        dp(68) to dp(48),
        dp(73) to dp(54),
        dp(80) to dp(37),
        dp(94) to dp(28),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        2 to 4,
        4 to 5,
        4 to 7,
        5 to 6,
        5 to 15,
        6 to 7,
        7 to 8,
        8 to 9,
        9 to 10,
        10 to 11,
        11 to 12,
        12 to 13,
        13 to 14,
        15 to 16,
        15 to 17,
        17 to 18,
        17 to 20,
        18 to 19,
        20 to 21
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