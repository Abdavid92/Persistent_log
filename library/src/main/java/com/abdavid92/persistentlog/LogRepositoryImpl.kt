package com.abdavid92.persistentlog

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

internal class LogRepositoryImpl(
    context: Context
) : ILogRepository {

    private val dao = DbContext.getInstance(context)
        .getIEventDao()

    private val dispatcher = Dispatchers.IO

    override suspend fun create(event: Event) = withContext(dispatcher) {
        dao.create(event)
    }

    override suspend fun update(event: Event) = withContext(dispatcher) {
        dao.update(event)
    }

    override suspend fun delete(event: Event) = withContext(dispatcher) {
        dao.delete(event)
    }

    override suspend fun delete(events: List<Event>) = withContext(dispatcher) {
        dao.delete(events)
    }

    override suspend fun all(): List<Event> = withContext(dispatcher) {
        return@withContext dao.all()
    }

    override suspend fun get(date: Date): Event? = withContext(dispatcher) {
        return@withContext dao.get(date)
    }

    override suspend fun get(tag: String): List<Event> = withContext(dispatcher) {
        return@withContext dao.get(tag)
    }

    override suspend fun get(type: EventType): List<Event> = withContext(dispatcher) {
        return@withContext dao.get(type)
    }

    override fun flow() = dao.flow()
}