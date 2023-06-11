package com.example.torneo.Core.Data




import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "torneo_table")
data class Torneo(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val nombre: String,
    val anio: Int?,
    val tipo: String?,

)

@Dao
interface TorneoDao {

    @Query("SELECT * FROM torneo_table ORDER BY id DESC")
    fun getAlphabetizedTorneos(): Flow<List<Torneo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(torneo: Torneo)

    @Query("DELETE FROM torneo_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteTorneo(torneo: Torneo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(torneo: Torneo)

    @Query("SELECT * from torneo_table WHERE id = :id")
    fun getTorneo(id: Int): Torneo
}

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