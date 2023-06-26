package com.example.torneo.Core.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.example.torneo.Core.Constantes.Companion.TORNEO_TABLE
import com.example.torneo.Core.Data.Entity.Torneo
import kotlinx.coroutines.flow.Flow

@Dao
interface TorneoDao{

    @Query("SELECT * FROM $TORNEO_TABLE ORDER BY id DESC")
    fun getTorneos(): Flow<List<Torneo>>

    @Insert(onConflict = IGNORE)
    fun insertTorneo(torneo: Torneo)

    @Query("DELETE FROM torneo_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteTorneo(torneo: Torneo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(torneo: Torneo)

    @Query("SELECT * from torneo_table WHERE id = :id")
    fun getTorneo(id: Int): Torneo
}

