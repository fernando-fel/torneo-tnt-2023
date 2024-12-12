package com.example.torneo.Core.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.torneo.Core.Data.Entity.Partido
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidoDao {
    @Query("SELECT * FROM partido_table")
    fun getPartidos(): Flow<List<Partido>>

    @Query("SELECT * FROM partido_table WHERE id = :idPartido" )
    fun getPartido(idPartido: Int): Partido
    @Query("SELECT * FROM partido_table WHERE idFecha = :fechaId")
    fun getPartidosByFechaId(fechaId: Int): Flow<List<Partido>>

    @Query("SELECT * FROM partido_table WHERE id = :partidoId")
    fun getPartidoById(partidoId: Int): Partido

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPartido(partido: Partido)

    @Update
    suspend fun updatePartido(partido: Partido)

    @Delete
    suspend fun deletePartido(partido: Partido)

    // Nueva consulta para obtener el conteo de equipos
    @Query("SELECT COUNT(*) FROM partido_table")
    fun getCantidadPartidos(): Int
/*
    @Query(
        """
        SELECT p.* FROM partido_table p
        WHERE p.dia = :date
        ORDER BY p.hora
    """
    )
    fun getPartidosByDate(date: String): Flow<List<Partido>>
*/
@Query("""
    SELECT p.id, p.idFecha, p.hora, p.dia, p.numCancha, 
           eLocal.nombre AS nombreLocal, 
           eVisitante.nombre AS nombreVisitante, 
           p.golLocal, p.golVisitante, p.estado, 
           p.resultado, p.idPersona, 
           f.numeroFecha, t.nombre AS nombreTorneo 
    FROM partido_table p 
    JOIN fecha_table f ON p.idFecha = f.id 
    JOIN torneo_table t ON f.idTorneo = t.id 
    JOIN equipo_table eLocal ON p.idLocal = eLocal.id
    JOIN equipo_table eVisitante ON p.idVisitante = eVisitante.id
    WHERE p.dia = :date
    ORDER BY p.hora
""")
fun getPartidosByDate(date: String): Flow<List<PartidoConDetalles>>

    data class PartidoConDetalles(
        val id: Int,
        val idFecha: String,
        val hora: String,
        val dia: String,
        val numCancha: String,
        val nombreLocal: String,
        val nombreVisitante: String,
        val golLocal: Int,
        val golVisitante: Int,
        val estado: String,
        val resultado: String,
        val idPersona: String,
        val numeroFecha: String,
        val nombreTorneo: String
    )

}