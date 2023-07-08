package com.example.torneo.Core.Data.repository
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.Core.Data.Jugador
import kotlinx.coroutines.flow.Flow


typealias Personas = List<Persona>
interface PersonaRepository {
    fun getPersonaFromRoom() : Flow<Personas>

    suspend fun addPersonaToRoom(persona: Persona)

    /**
     * Delete item from the data source
     */
    suspend fun deletePersona(persona: Persona)

    /**
     * Update item in the data source
     */
    suspend fun updatePersona(persona: Persona)

    suspend fun getPersona(id: Int): Persona

    //fun getPersonasPorEquipo(id: Int): Flow<Personas>
}


