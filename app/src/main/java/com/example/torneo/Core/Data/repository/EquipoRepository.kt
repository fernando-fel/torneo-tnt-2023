package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.Entity.Equipo
import kotlinx.coroutines.flow.Flow


typealias Equipos = List<Equipo>
interface EquipoRepository {
    fun getEquipoFromRoom() : Flow<Equipos>

    fun addEquipoToRoom(equipo: Equipo)

    /**
     * Delete item from the data source
     */
    suspend fun deleteEquipo(equipo: Equipo)

    /**
     * Update item in the data source
     */
    suspend fun updateEquipo(equipo: Equipo)

    suspend fun getEquipo(id: Int): Equipo
}
