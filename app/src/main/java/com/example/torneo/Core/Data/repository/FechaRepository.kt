package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Torneo
import kotlinx.coroutines.flow.Flow

interface FechaRepository {

    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllFechas(): Flow<List<Fecha>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    suspend fun getFecha(id: Int): Fecha

    fun getFechasByTorneo(id: Int): Flow<List<Fecha>>
    /**
     * Insert item in the data source
     */
    suspend fun addFecha(fecha: Fecha)

    /**
     * Delete item from the data source
     */
    suspend fun deleteFecha(fecha: Fecha)

    /**
     * Update item in the data source
     */
    suspend fun updateFecha(fecha: Fecha)

}

