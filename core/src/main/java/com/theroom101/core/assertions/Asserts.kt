package com.theroom101.core.assertions

import com.theroom101.core.BuildConfig
import java.lang.IllegalStateException

object Asserts {

    var isEnabled = BuildConfig.DEBUG

    fun assertTrue(condition: Boolean, msg: () -> String = { "" }) {
        if (!condition) fail(msg)
    }

    fun fail(msg: () -> String): Nothing {
        throw IllegalStateException(msg.invoke())
    }
}