package com.example.torneo.Core.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Jugador
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidoDao {
    @Query("SELECT * FROM partido_table")
    fun getPartidos(): Flow<List<Partido>>

    @Query("SELECT * FROM partido_table WHERE idFecha = :fechaId")
    fun getPartidosByFechaId(fechaId: Int): Flow<List<Partido>>

    @Query("SELECT * FROM partido_table WHERE id = :partidoId")
    fun getPartidoById(partidoId: Int): Partido

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPartido(partido: Partido)

    @Update
    suspend fun updatePartido(partido: Partido)

    @Delete
    suspend fun deletePartido(partido: Partido)
}
