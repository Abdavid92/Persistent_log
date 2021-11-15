package com.abdavid92.persistentlog

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.io.Closeable
import java.util.*

@Dao
interface IEventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Delete
    suspend fun delete(events: List<Event>)

    @Query("SELECT * FROM events WHERE date = :date")
    suspend fun get(date: Date): Event?

    @Query("SELECT * FROM events WHERE tag = :tag")
    suspend fun get(tag: String): List<Event>

    @Query("SELECT * FROM events WHERE type = :type")
    suspend fun get(type: EventType): List<Event>

    @Query("SELECT * FROM events")
    suspend fun all(): List<Event>

    @Query("SELECT * FROM events")
    fun flow(): Flow<List<Event>>
}