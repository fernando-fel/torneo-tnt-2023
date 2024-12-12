package com.example.torneo.Core.Data.repository
import androidx.room.Query
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.Core.Data.Jugador
import kotlinx.coroutines.flow.Flow


typealias Personas = List<Persona>
interface PersonaRepository {

    fun getAllPersonas() : Flow<Personas>

    suspend fun addPersona(persona: Persona)

    /**
     * Delete item from the data source
     */
    suspend fun deletePersona(persona: Persona)

    /**
     * Update item in the data source
     */
    suspend fun updatePersona(persona: Persona)

    suspend fun getPersona(id: Int): Persona?

    suspend fun getPersonaList(): List<Persona>

    fun getCountPersonas(): Int
}


//la idea es poder estar conectado los 3, uno es juez, el otro visualizar los resultados
//

