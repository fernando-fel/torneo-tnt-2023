package com.example.torneo.Core.Data.repository


import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Torneo

import kotlinx.coroutines.flow.Flow

typealias Torneos = List<Torneo>

interface TorneoRepository {

    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllTorneos(): Flow<Torneos>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    suspend fun getTorneo(id: Int): Torneo

    /**
     * Insert item in the data source
     */
    suspend fun addTorneo(torneo: Torneo)

    /**
     * Delete item from the data source
     */
    suspend fun deleteTorneo(torneo: Torneo)

    /**
     * Update item in the data source
     */
    suspend fun updateTorneo(torneo: Torneo)

    suspend fun inscribirEquipos(torneoId: Int, equipos: List<Equipo>)
    suspend fun getEquiposEnTorneo(torneoId: String): List<Equipo>
    fun getCountTorneos(): Int
}
