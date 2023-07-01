package com.example.torneo.Core.BaseDeDatos
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.torneo.Core.Data.Dao.EquipoDao
import com.example.torneo.Core.Data.Dao.FechaDao
import com.example.torneo.Core.Data.Dao.JugadorDao
import com.example.torneo.Core.Data.Dao.PartidoDao
import com.example.torneo.Core.Data.Dao.PersonaDao
import com.example.torneo.Core.Data.Dao.TorneoDao
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Core.Data.Jugador


@Database(entities = [Torneo::class, Equipo::class, Jugador::class, Fecha::class, Partido::class, Persona::class], version=7, exportSchema = false)
abstract class TorneoDB: RoomDatabase() {
    abstract fun torneoDao(): TorneoDao

    abstract fun equipoDao(): EquipoDao

    abstract fun jugadorDao() : JugadorDao

    abstract fun fechaDao(): FechaDao

    abstract fun partidoDao(): PartidoDao

    abstract fun personaDao(): PersonaDao

    //abstract fun equipoJugadorDao() : EquipoJugadorDao


}
/*
@Database(entities = [Torneo::class, Equipo::class], version = 2)
abstract class TorneoDB : RoomDatabase() {
    // Define los DAOs (Data Access Objects) para acceder a las tablas
    abstract fun torneoDao(): TorneoDao
    abstract fun equipoDao(): EquipoDao
companion object {
    @Volatile
    private var INSTANCE: TorneoDB? = null

    fun getDatabase(context: Context): TorneoDB {
        val tempInstance = INSTANCE
        if (tempInstance != null) {
            return tempInstance
        }
        synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TorneoDB::class.java,
                "mi_base_de_datos"
            ).build()
            INSTANCE = instance
            return instance
        }
    }
}
    }




import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.torneobd.Data.TorneoDao

/**
 * Database class with a singleton INSTANCE object.
 */
@Database(entities = [Torneo::class], version = 1, exportSchema = false)
abstract class TorneoDB : RoomDatabase() {
    abstract fun torneoDao(): TorneoDao

}
*/