package com.abdavid92.persistentlog

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(
    tableName = "events"
)
data class Event(
    @PrimaryKey
    @SerializedName("date")
    val date: Date,

    @SerializedName("type")
    val type: EventType,

    @SerializedName("tag")
    val tag: String?,

    @SerializedName("msg")
    val msg: String,

    @SerializedName("throwable")
    val throwable: String?
)