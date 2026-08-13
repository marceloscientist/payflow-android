package io.payflow.android.core.utils

import android.util.Log

object Logger {

    fun debug(
        tag: String,
        message: String
    ) {
        Log.d(tag, message)
    }

    fun error(
        tag: String,
        message: String,
        throwable: Throwable? = null
    ) {
        Log.e(tag, message, throwable)
    }
}