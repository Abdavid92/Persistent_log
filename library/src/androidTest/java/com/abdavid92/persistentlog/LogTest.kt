package com.abdavid92.persistentlog

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test

private const val TAG = "LogTest"

class LogTest {

    @Test
    fun d() {

        runBlocking {
            Log.d(TAG, "Log de depuración")
        }
    }

    @Test
    fun i() {

        Log.i(TAG, "Log de info")
    }

    @Test
    fun w() {

        Log.w(TAG, "Log de advertencia")
    }

    @Test
    fun e() {

        Log.e(TAG, "Log de error")
    }
}