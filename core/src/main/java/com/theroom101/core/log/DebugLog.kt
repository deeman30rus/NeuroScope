package com.theroom101.core.log

import android.util.Log

interface Logger {

    fun log(block: () -> String)
}

private open class LoggerImpl(
    private val tag: String
): Logger {

    override fun log(block: () -> String) {
        Log.v(tag, block.invoke())
    }

    companion object {

        fun create(tag: String) = LoggerImpl(tag)
    }
}

object DebugLog {

    const val DEFAULT_TAG = "delizarov"

    private val loggers = mutableMapOf<String, Logger>(
        DEFAULT_TAG to LoggerImpl.create(DEFAULT_TAG)
    )

    val default: Logger
        get() = acquire(DEFAULT_TAG)

    fun acquire(tag: String): Logger {
        val logger = loggers[tag] ?: LoggerImpl(tag).also {
            loggers[tag] = it
        }

        return logger
    }

    fun release(tag: String) {
        loggers.remove(tag)
    }
}