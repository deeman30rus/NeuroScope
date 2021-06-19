package com.theroom101.core.assertions

import com.theroom101.core.BuildConfig
import java.lang.IllegalStateException

typealias LazyMessage = () -> String

object Asserts {

    var isEnabled = BuildConfig.DEBUG

    fun assertTrue(condition: Boolean, msg: LazyMessage = { "" }) {
        if (!condition) fail(msg)
    }

    fun assertNull(obj: Any?, msg: LazyMessage = { "" }) {
        if (obj != null) fail(msg)
    }

    fun fail(msg: LazyMessage): Nothing {
        throw IllegalStateException(msg.invoke())
    }
}