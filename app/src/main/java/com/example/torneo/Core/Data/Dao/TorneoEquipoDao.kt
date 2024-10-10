package com.example.torneo.Core.Data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.TorneoEquipo
import kotlinx.coroutines.flow.Flow

@Dao
interface TorneoEquipoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(torneoEquipo: TorneoEquipo)

    @Query("""
    SELECT e.id, e.nombre 
    FROM torneo_equipo te 
    INNER JOIN equipo_table e ON te.equipoId = e.id 
    WHERE te.torneoId = :torneoId
""")
    fun getEquiposByTorneo(torneoId: Int): Flow<List<Equipo>>

    @Query("DELETE FROM torneo_equipo WHERE torneoId = :torneoId")
    suspend fun deleteEquiposByTorneo(torneoId: Int)
}
