package com.abdavid92.persistentlog.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdavid92.persistentlog.Log
import com.abdavid92.persistentlog.LogManager
import kotlinx.coroutines.runBlocking

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Nada")

        Log.d(TAG, "Otro nada")

        val manager = LogManager.newInstance()

        runBlocking {

            val all = manager.all()


            assert(all.isNotEmpty())


        }
    }
}