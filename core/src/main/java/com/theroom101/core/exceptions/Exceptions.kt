package com.theroom101.core.exceptions

fun error(msg: () -> String): Nothing {
    throw IllegalStateException(msg())
}