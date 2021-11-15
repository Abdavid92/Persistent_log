package com.abdavid92.persistentlog

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Event::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
internal abstract class DbContext : RoomDatabase() {

    abstract fun getIEventDao(): IEventDao

    companion object {

        private var INSTANCE: DbContext? = null

        fun getInstance(context: Context): DbContext {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        DbContext::class.java,
                        "logs.db")
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}