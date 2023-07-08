package com.example.torneo.Core.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.Core.Data.Jugador
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonaDao{

    @Query("SELECT * FROM persona_table WHERE id = :personaId")
    fun getPersonaByEquipo(personaId: Int): Flow<List<Persona>>

    @Query("SELECT * FROM persona_table ORDER BY id ASC")
    fun getAlphabetizedPersona(): Flow<List<Persona>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPersona(persona:Persona)

    @Delete
    suspend fun deletePersona(persona: Persona)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updatePersona(persona: Persona)

    @Query("DELETE FROM persona_table")
    suspend fun deleteAll()

    @Query("SELECT * from persona_table WHERE idPersona = :idPersona")
    suspend fun getPersona(idPersona: Int): Persona
}

