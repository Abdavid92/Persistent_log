package com.abdavid92.persistentlog

import androidx.room.TypeConverter
import java.util.*

internal class DateConverter {

    @TypeConverter
    fun toDate(time: Long): Date {
        return Date(time)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
}