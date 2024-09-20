package com.example.torneo.Core.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.torneo.Core.Data.Entity.Equipo
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipoDao {
    @Query("SELECT * FROM equipo_table ORDER BY id ASC")
    fun getAlphabetizedEquipo(): Flow<List<Equipo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEquipo(equipo: Equipo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateEquipo(equipo: Equipo)

    @Delete
    fun deleteEquipo(equipo: Equipo)

    @Query("DELETE FROM equipo_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(equipos: List<Equipo>)

    @Query("SELECT * from equipo_table WHERE id = :id")
    fun getEquipo(id: Int): Equipo
}
