package com.example.torneo.Core.Data.Dao

import androidx.room.*
import com.example.torneo.Core.Data.Entity.Fecha
import kotlinx.coroutines.flow.Flow

@Dao
interface FechaDao {

    @Query("SELECT * FROM fecha_table")
    fun getFechas(): Flow<List<Fecha>>
    @Query("SELECT * FROM fecha_table Where id = :idFecha ")
    fun getFecha(idFecha: Int): Fecha

    @Query("SELECT * FROM fecha_table WHERE idTorneo = :idTorneo")
    fun getFechasByTorneoId(idTorneo: Int): Flow<List<Fecha>>

    @Query("SELECT * FROM fecha_table WHERE id = :idFecha")
    suspend fun getFechaById(idFecha: Int): Fecha

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFecha(fecha: Fecha)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(fechas: List<Fecha>)

    @Update
    suspend fun updateFecha(fecha: Fecha)
    @Delete
    suspend fun deleteFecha(fecha: Fecha)

    @Query("DELETE FROM fecha_table")
    suspend fun deleteAll()
    // Nueva consulta para obtener el conteo de equipos
    @Query("SELECT COUNT(*) FROM fecha_table")
    fun getCantidadFechas(): Int
}
