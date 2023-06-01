package com.example.torneo.Core.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.torneo.Core.Data.Jugador
import kotlinx.coroutines.flow.Flow

@Dao
interface JugadorDao{

    @Query("SELECT * FROM jugador_table ORDER BY nombre ASC")
    fun getAlphabetizedJugador(): Flow<List<Jugador>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(jugador: Jugador)

    @Delete
    suspend fun delete(jugador: Jugador)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(jugador: Jugador)

    @Query("DELETE FROM jugador_table")
    suspend fun deleteAll()

    @Query("SELECT * from jugador_table WHERE dni = :dni")
    fun getJugador(dni: Int): Flow<Jugador>
}

