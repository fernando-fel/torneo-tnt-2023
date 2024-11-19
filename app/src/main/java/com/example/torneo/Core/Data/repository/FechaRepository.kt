package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Torneo
import kotlinx.coroutines.flow.Flow

typealias Fechas = List<Fecha>

interface FechaRepository {
    fun getFechasFromRoom(): Flow<List<Fecha>>

    fun getFechasByTorneoIdFromRoom(idTorneo: Int): Flow<List<Fecha>>

    suspend fun getFechaById(idFecha: Int): Fecha

    fun addFechaToRoom(fecha: Fecha)

    suspend fun deleteFecha(fecha: Fecha)

    suspend fun updateFecha(fecha: Fecha)

    // Sync methods
    fun syncFirebaseToRoom()

    fun getAllFechas(): Flow<List<Fecha>>

    suspend fun getFecha(id: Int): Fecha

    suspend fun addFecha(fecha: Fecha)

    fun getFechasByTorneo(id: Int): Flow<List<Fecha>>

    fun getCountFechas(): Int
}
