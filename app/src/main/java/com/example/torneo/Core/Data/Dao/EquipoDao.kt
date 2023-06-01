package com.example.torneo.Core.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.torneo.Core.Data.Equipo
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipoDao{

    @Query("SELECT * FROM equipo_table ORDER BY id ASC")
    fun getAlphabetizedEquipo(): Flow<List<Equipo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(equipo: Equipo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(equipo: Equipo)

    @Delete
    suspend fun delete(equipo: Equipo)

    @Query("DELETE FROM equipo_table")
    suspend fun deleteAll()

    @Query("SELECT * from equipo_table WHERE nombre = :nombre")
    fun getEquipo(nombre: String): Flow<Equipo>
}