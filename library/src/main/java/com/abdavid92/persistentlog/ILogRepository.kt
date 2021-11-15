package com.abdavid92.persistentlog

import kotlinx.coroutines.flow.Flow
import java.io.Closeable
import java.util.*

interface ILogRepository {

    suspend fun create(event: Event)

    suspend fun update(event: Event)

    suspend fun delete(event: Event)

    suspend fun delete(events: List<Event>)

    suspend fun all(): List<Event>

    suspend fun get(date: Date): Event?

    suspend fun get(tag: String): List<Event>

    suspend fun get(type: EventType): List<Event>

    fun flow(): Flow<List<Event>>
}