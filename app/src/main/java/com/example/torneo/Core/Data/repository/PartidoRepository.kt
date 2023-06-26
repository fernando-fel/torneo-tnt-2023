package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.Entity.Partido

import com.example.torneo.Core.Data.Entity.Torneo
import kotlinx.coroutines.flow.Flow

typealias Partidos = List<Partido>

interface PartidoRepository {

    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllPartidos(): Flow<Partidos>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getPartido(id: Int): Partido

    /**
     * Insert item in the data source
     */
    suspend fun addPartido(partido : Partido)

    /**
     * Delete item from the data source
     */
    suspend fun deletePartido(partido: Partido)

    /**
     * Update item in the data source
     */
    suspend fun updatePartido(partido: Partido)

}
