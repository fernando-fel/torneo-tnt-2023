package com.example.torneo.Core.Data

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

data class EquipoJugador(
    @Embedded val equipo: Equipo,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val jugador: List<Jugador>
)

@Dao
interface EquipoJugadorDao{
    @Transaction
    @Query("SELECT * FROM equipo_table")
    fun getJugadoresDeEquipo(): Flow<List<Jugador>>
}