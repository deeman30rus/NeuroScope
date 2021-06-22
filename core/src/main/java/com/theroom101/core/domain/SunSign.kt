package com.theroom101.core.domain

// todo make abstract to change sun sign set
// todo move out from core

enum class SunSign {
    Capricorn,
    Aquarius,
    Pisces,
    Aries,
    Taurus,
    Gemini,
    Cancer,
    Leo,
    Virgo,
    Libra,
    Scorpio,
    Ophiuchus,
    Sagittarius;

    fun next(): SunSign {
        return values()[(ordinal + 1) % values().size]
    }

    fun prev(): SunSign {
        return values()[(ordinal + values().size - 1) % values().size]
    }
}
