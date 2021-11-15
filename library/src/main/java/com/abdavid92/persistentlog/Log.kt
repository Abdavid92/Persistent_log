package com.abdavid92.persistentlog

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

object Log {

    private val scope = CoroutineScope(Dispatchers.IO)

    fun d(tag: String?, msg: String): Int {
        return d(tag, msg, null)
    }

    fun d(tag: String?, msg: String, tr: Throwable?): Int {

        createEvent(Event(
            Date(),
            EventType.Debug,
            tag,
            msg,
            Log.getStackTraceString(tr)
        ))

        return Log.d(tag, msg, tr)
    }

    fun i(tag: String?, msg: String): Int {
        return i(tag, msg, null)
    }

    fun i(tag: String?, msg: String, tr: Throwable?): Int {

        createEvent(
            Event(
                Date(),
                EventType.Info,
                tag,
                msg,
                Log.getStackTraceString(tr)
            )
        )

        return Log.i(tag, msg, tr)
    }

    fun w(tag: String?, msg: String): Int {
        return w(tag, msg, null)
    }

    fun w(tag: String?, msg: String, tr: Throwable?): Int {

        createEvent(
            Event(
                Date(),
                EventType.Warning,
                tag,
                msg,
                Log.getStackTraceString(tr)
            )
        )

        return Log.w(tag, msg, tr)
    }

    fun e(tag: String?, msg: String): Int {
        return e(tag, msg, null)
    }

    fun e(tag: String?, msg: String, tr: Throwable?): Int {

        createEvent(
            Event(
                Date(),
                EventType.Error,
                tag,
                msg,
                Log.getStackTraceString(tr)
            )
        )

        return Log.e(tag, msg, tr)
    }

    private fun createEvent(event: Event) {
        scope.launch {
            LogRepositoryImpl(PersistentLogProvider.mContext)
                .create(event)
        }
    }
}