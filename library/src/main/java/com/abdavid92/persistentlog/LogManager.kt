package com.abdavid92.persistentlog

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.Closeable
import java.io.FileOutputStream
import java.util.*
import kotlin.coroutines.CoroutineContext

class LogManager private constructor(
    private val context: Application
) {

    private val repository = LogRepositoryImpl(context)

    private val gson = Gson()

    /**
     * Obtiene un flow con todos los eventos guardados.
     * */
    fun flow() = repository.flow()

    /**
     * Obtiene todos los eventos guardados.
     *
     * @return [List]
     * */
    suspend fun all() = repository.all()

    /**
     * Obtiene un evento por la fecha de creación.
     *
     * @return [Event]
     * */
    suspend fun get(date: Date) = repository.get(date)

    /**
     * Obtiene todos los eventos que conincidad con el tag.
     *
     * @return [List]
     * */
    suspend fun get(tag: String) = repository.get(tag)

    /**
     * Obtiene todos los eventos del tipo dado.
     *
     * @return [List]
     * */
    suspend fun get(type: EventType) = repository.get(type)

    /**
     * Elimina un evento.
     * */
    suspend fun delete(event: Event) = repository.delete(event)

    /**
     * Elimina todos los eventos guardados.
     * */
    suspend fun clear() {
        repository.delete(repository.all())
    }

    /**
     * Exporta todos los eventos a json.
     *
     * @return [String] Eventos convertidos a json.
     * */
    suspend fun exportToJson(): String {
        val list = repository.all()

        return gson.toJson(list)
    }

    /**
     * Exporta todos los eventos a json que coincidan con el tag.
     *
     * @return [String] Eventos convertidos a json.
     * */
    suspend fun exportToJson(tag: String): String {
        val list = repository.get(tag)

        return gson.toJson(list)
    }

    /**
     * Exporta todos los eventos a json del tipo dado.
     *
     * @return [String] Eventos convertidos a json.
     * */
    suspend fun exportToJson(eventType: EventType): String {
        val list = repository.get(eventType)

        return gson.toJson(list)
    }

    /**
     * Exporta todos los eventos a un archivo.
     *
     * @param fileUri - Uri del archivo donde se va a exportar los eventos.
     * El uri debe tener un esquema que coincida con [ContentResolver.SCHEME_FILE]
     * de lo contrario la exportación fallará.
     *
     * @return [Boolean] `true` si la exportación tuvo éxito.
     * */
    suspend fun exportToFile(fileUri: Uri): Boolean {

        val json = exportToJson()

        return exportJsonToFile(fileUri, json)
    }

    suspend fun exportToFile(fileUri: Uri, tag: String): Boolean {

        val json = exportToJson(tag)

        return exportJsonToFile(fileUri, json)
    }

    suspend fun exportToFile(fileUri: Uri, type: EventType): Boolean {

        val json = exportToJson(type)

        return exportJsonToFile(fileUri, json)
    }

    private suspend fun exportJsonToFile(fileUri: Uri, json: String): Boolean {

        if (fileUri.scheme != ContentResolver.SCHEME_FILE)
            return false

        runCatching {

            return withContext(Dispatchers.IO) {

                context.contentResolver.openOutputStream(fileUri, "w")?.use {
                    it.write(json.toByteArray())
                }

                return@withContext true
            }
        }

        return false
    }

    companion object {

        @Volatile
        private var INSTANCE: LogManager? = null

        fun newInstance(): LogManager {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = LogManager(PersistentLogProvider
                        .mContext
                        .applicationContext as Application)

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}