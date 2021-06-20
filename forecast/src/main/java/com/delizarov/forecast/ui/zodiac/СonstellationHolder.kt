package com.delizarov.forecast.ui.zodiac

import android.graphics.Point
import com.delizarov.forecast.ui.nightsky.stars.StarModel
import com.delizarov.forecast.ui.nightsky.stars.StarSize
import com.delizarov.forecast.ui.nightsky.stars.StarType
import com.theroom101.core.android.dp
import com.theroom101.core.domain.SunSign

internal object Constellations {
    private val constellations = mapOf(
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

    val stars: List<StarModel> = constellations.flatMap { it.value.stars }.toList()

    operator fun get(sunSign: SunSign): Constellation {
        return constellations[sunSign] ?: error("Unknown constellation for $sunSign")
    }
}

private val capricorn = Constellation(
    stars = listOf(
        star(dp(49), dp(8), StarType.Star1, StarSize.Medium),
        star(dp(51), dp(16), StarType.Star1, StarSize.Large),
        star(dp(70), dp(62), StarType.Star1, StarSize.Large),
        star(dp(71), dp(69), StarType.Star1, StarSize.ExtraLarge),
        star(dp(59), dp(73), StarType.Star1, StarSize.Large),
        star(dp(42), dp(82), StarType.Star1, StarSize.Large),
        star(dp(24), dp(87), StarType.Star1, StarSize.Large),
        star(dp(12), dp(90), StarType.Star1, StarSize.ExtraLarge),
        star(dp(14), dp(84), StarType.Star1, StarSize.Medium),
        star(dp(24), dp(72), StarType.Star1, StarSize.Large),
        star(dp(33), dp(61), StarType.Star1, StarSize.Large),
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

private val aquarius = Constellation(
    stars = listOf(
        star(dp(88), dp(6), StarType.Star1, StarSize.Medium),
        star(dp(59), dp(26), StarType.Star1, StarSize.ExtraLarge),
        star(dp(32), dp(43), StarType.Star1, StarSize.ExtraLarge),
        star(dp(56), dp(52), StarType.Star1, StarSize.ExtraLarge),
        star(dp(78), dp(51), StarType.Star1, StarSize.Medium),
        star(dp(31), dp(54), StarType.Star1, StarSize.Medium),
        star(dp(28), dp(58), StarType.Star1, StarSize.Large),
        star(dp(23), dp(63), StarType.Star1, StarSize.ExtraLarge),
        star(dp(44), dp(91), StarType.Star1, StarSize.ExtraLarge),
        star(dp(49), dp(78), StarType.Star1, StarSize.Large),
        star(dp(65), dp(76), StarType.Star1, StarSize.ExtraLarge),
        star(dp(70), dp(78), StarType.Star1, StarSize.Large),
        star(dp(83), dp(88), StarType.Star1, StarSize.Large),
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
private val pisces = Constellation(
    stars = listOf(
        star(dp(14), dp(90), StarType.Star1, StarSize.Medium),
        star(dp(25), dp(94), StarType.Star1, StarSize.Large),
        star(dp(30), dp(84), StarType.Star1, StarSize.ExtraLarge),
        star(dp(55), dp(84), StarType.Star1, StarSize.Large),
        star(dp(72), dp(86), StarType.Star1, StarSize.Large),
        star(dp(92), dp(88), StarType.Star1, StarSize.ExtraLarge),
        star(dp(80), dp(80), StarType.Star1, StarSize.Large),
        star(dp(66), dp(66), StarType.Star1, StarSize.ExtraLarge),
        star(dp(61), dp(58), StarType.Star1, StarSize.Large),
        star(dp(50), dp(34), StarType.Star1, StarSize.Large),
        star(dp(48), dp(22), StarType.Star1, StarSize.Medium),
        star(dp(41), dp(17), StarType.Star1, StarSize.Medium),
        star(dp(43), dp(10), StarType.Star1, StarSize.Medium),
        star(dp(48), dp(8), StarType.Star1, StarSize.Medium),
        star(dp(58), dp(9), StarType.Star1, StarSize.Medium),
        star(dp(58), dp(19), StarType.Star1, StarSize.Medium),
        star(dp(54), dp(24), StarType.Star1, StarSize.Medium),
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

private val aries = Constellation(
    stars = listOf(
        star(dp(26), dp(40), StarType.Star1, StarSize.Large),
        star(dp(57), dp(46), StarType.Star1, StarSize.ExtraLarge),
        star(dp(70), dp(53), StarType.Star1, StarSize.Large),
        star(dp(72), dp(59), StarType.Star1, StarSize.Medium),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3
    )
)

private val taurus = Constellation(
    stars = listOf(
        star(dp(18), dp(26), StarType.Star1, StarSize.ExtraLarge),
        star(dp(44), dp(49), StarType.Star1, StarSize.Large),
        star(dp(49), dp(52), StarType.Star1, StarSize.Medium),
        star(dp(54), dp(55), StarType.Star1, StarSize.Medium),
        star(dp(64), dp(64), StarType.Star1, StarSize.ExtraLarge),
        star(dp(84), dp(78), StarType.Star1, StarSize.Large),
        star(dp(88), dp(82), StarType.Star1, StarSize.Medium),
        star(dp(53), dp(49), StarType.Star1, StarSize.Medium),
        star(dp(50), dp(43), StarType.Star1, StarSize.Medium),
        star(dp(47), dp(30), StarType.Star1, StarSize.Large),
        star(dp(32), dp(10), StarType.Star1, StarSize.ExtraLarge),
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

private val gemini = Constellation(
    stars = listOf(
        star(dp(20), dp(23), StarType.Star1, StarSize.ExtraLarge),
        star(dp(26), dp(23), StarType.Star1, StarSize.Large),
        star(dp(34), dp(24), StarType.Star1, StarSize.Medium),
        star(dp(61), dp(38), StarType.Star1, StarSize.Large),
        star(dp(76), dp(43), StarType.Star1, StarSize.Large),
        star(dp(82), dp(41), StarType.Star1, StarSize.Medium),
        star(dp(72), dp(49), StarType.Star1, StarSize.Medium),
        star(dp(66), dp(61), StarType.Star1, StarSize.ExtraLarge),
        star(dp(62), dp(73), StarType.Star1, StarSize.Large),
        star(dp(42), dp(52), StarType.Star1, StarSize.Large),
        star(dp(29), dp(49), StarType.Star1, StarSize.Large),
        star(dp(13), dp(42), StarType.Star1, StarSize.Medium),
        star(dp(11), dp(35), StarType.Star1, StarSize.Large),
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
private val cancer = Constellation(
    stars = listOf(
        star(dp(31), dp(17), StarType.Star1, StarSize.ExtraLarge),
        star(dp(43), dp(33), StarType.Star1, StarSize.Large),
        star(dp(46), dp(40), StarType.Star1, StarSize.Large),
        star(dp(45), dp(64), StarType.Star1, StarSize.ExtraLarge),
        star(dp(78), dp(54), StarType.Star1, StarSize.ExtraLarge),
    ),
    edges = setOf(
        0 to 1,
        1 to 2,
        2 to 3,
        2 to 4
    )
)

private val leo = Constellation(
    stars = listOf(
        star(dp(57), dp(14), StarType.Star1, StarSize.Medium),
        star(dp(48), dp(14), StarType.Star1, StarSize.Large),
        star(dp(48), dp(31), StarType.Star1, StarSize.Medium),
        star(dp(55), dp(39), StarType.Star1, StarSize.Large),
        star(dp(65), dp(37), StarType.Star1, StarSize.Medium),
        star(dp(75), dp(45), StarType.Star1, StarSize.ExtraLarge),
        star(dp(41), dp(75), StarType.Star1, StarSize.Large),
        star(dp(29), dp(89), StarType.Star1, StarSize.ExtraLarge),
        star(dp(32), dp(64), StarType.Star1, StarSize.Large),
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

private val virgo = Constellation(
    stars = listOf(
        star(dp(66), dp(7), StarType.Star1, StarSize.Medium),
        star(dp(65), dp(25), StarType.Star1, StarSize.Large),
        star(dp(61), dp(37), StarType.Star1, StarSize.Large),
        star(dp(63), dp(56), StarType.Star1, StarSize.Large),
        star(dp(71), dp(69), StarType.Star1, StarSize.Large),
        star(dp(58), dp(87), StarType.Star1, StarSize.Large),
        star(dp(53), dp(86), StarType.Star1, StarSize.Large),
        star(dp(46), dp(98), StarType.Star1, StarSize.Medium),
        star(dp(27), dp(90), StarType.Star1, StarSize.Medium),
        star(dp(38), dp(70), StarType.Star1, StarSize.Large),
        star(dp(48), dp(61), StarType.Star1, StarSize.ExtraLarge),
        star(dp(49), dp(40), StarType.Star1, StarSize.ExtraLarge),
        star(dp(31), dp(34), StarType.Star1, StarSize.Large),
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

private val libra = Constellation(
    stars = listOf(
        star(dp(26), dp(46), StarType.Star1, StarSize.Medium),
        star(dp(35), dp(41), StarType.Star1, StarSize.Large),
        star(dp(42), dp(35), StarType.Star1, StarSize.Large),
        star(dp(51), dp(16), StarType.Star1, StarSize.ExtraLarge),
        star(dp(75), dp(32), StarType.Star1, StarSize.ExtraLarge),
        star(dp(71), dp(66), StarType.Star1, StarSize.ExtraLarge),
        star(dp(53), dp(79), StarType.Star1, StarSize.Large),
        star(dp(52), dp(85), StarType.Star1, StarSize.Medium),
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

private val scorpio = Constellation(
    stars = listOf(
        star(dp(16), dp(63), StarType.Star1, StarSize.Medium),
        star(dp(9), dp(70), StarType.Star1, StarSize.Large),
        star(dp(7), dp(76), StarType.Star1, StarSize.ExtraLarge),
        star(dp(14), dp(86), StarType.Star1, StarSize.Large),
        star(dp(30), dp(87), StarType.Star1, StarSize.ExtraLarge),
        star(dp(42), dp(82), StarType.Star1, StarSize.Large),
        star(dp(46), dp(70), StarType.Star1, StarSize.Large),
        star(dp(48), dp(57), StarType.Star1, StarSize.Large),
        star(dp(61), dp(38), StarType.Star1, StarSize.Medium),
        star(dp(67), dp(35), StarType.Star1, StarSize.Medium),
        star(dp(72), dp(31), StarType.Star1, StarSize.Medium),
        star(dp(91), dp(25), StarType.Star1, StarSize.ExtraLarge),
        star(dp(87), dp(15), StarType.Star1, StarSize.Large),
        star(dp(90), dp(34), StarType.Star1, StarSize.Large),
        star(dp(89), dp(45), StarType.Star1, StarSize.Medium),
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

private val ophiuchus = Constellation(
    stars = listOf(
        star(dp(2), dp(25), StarType.Star1, StarSize.Medium),
        star(dp(21), dp(39), StarType.Star1, StarSize.Large),
        star(dp(32), dp(52), StarType.Star1, StarSize.Large),
        star(dp(37), dp(27), StarType.Star1, StarSize.Medium),
        star(dp(40), dp(24), StarType.Star1, StarSize.Large),
        star(dp(44), dp(8), StarType.Star1, StarSize.ExtraLarge),
        star(dp(54), dp(3), StarType.Star1, StarSize.Large),
        star(dp(63), dp(14), StarType.Star1, StarSize.ExtraLarge),
        star(dp(76), dp(30), StarType.Star1, StarSize.ExtraLarge),
        star(dp(85), dp(41), StarType.Star1, StarSize.Large),
        star(dp(97), dp(24), StarType.Star1, StarSize.Medium),
        star(dp(83), dp(43), StarType.Star1, StarSize.Large),
        star(dp(73), dp(55), StarType.Star1, StarSize.Large),
        star(dp(56), dp(64), StarType.Star1, StarSize.Large),
        star(dp(43), dp(64), StarType.Star1, StarSize.Medium),
        star(dp(51), dp(83), StarType.Star1, StarSize.ExtraLarge),
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

private val sagittarius = Constellation(
    stars = listOf(
        star(dp(21), dp(7), StarType.Star1, StarSize.Medium),
        star(dp(33), dp(15), StarType.Star1, StarSize.Medium),
        star(dp(37), dp(15), StarType.Star1, StarSize.Large),
        star(dp(43), dp(12), StarType.Star1, StarSize.Medium),
        star(dp(46), dp(29), StarType.Star1, StarSize.Medium),
        star(dp(53), dp(29), StarType.Star1, StarSize.Medium),
        star(dp(43), dp(37), StarType.Star1, StarSize.ExtraLarge),
        star(dp(39), dp(31), StarType.Star1, StarSize.Medium),
        star(dp(19), dp(28), StarType.Star1, StarSize.Large),
        star(dp(8), dp(40), StarType.Star1, StarSize.Large),
        star(dp(5), dp(43), StarType.Star1, StarSize.ExtraLarge),
        star(dp(18), dp(62), StarType.Star1, StarSize.Large),
        star(dp(26), dp(74), StarType.Star1, StarSize.ExtraLarge),
        star(dp(40), dp(76), StarType.Star1, StarSize.Large),
        star(dp(37), dp(67), StarType.Star1, StarSize.Medium),
        star(dp(64), dp(25), StarType.Star1, StarSize.Large),
        star(dp(70), dp(10), StarType.Star1, StarSize.Large),
        star(dp(70), dp(35), StarType.Star1, StarSize.Medium),
        star(dp(68), dp(48), StarType.Star1, StarSize.ExtraLarge),
        star(dp(73), dp(54), StarType.Star1, StarSize.Medium),
        star(dp(80), dp(37), StarType.Star1, StarSize.Large),
        star(dp(94), dp(28), StarType.Star1, StarSize.Large),
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



private fun star(x: Int, y: Int, type: StarType, size: StarSize) = StarModel(
    type,
    Point(x, y),
    size.size(),
)