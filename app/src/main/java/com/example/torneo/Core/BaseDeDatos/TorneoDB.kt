package com.example.torneo.Core.BaseDeDatos
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.torneo.Core.Data.Dao.EquipoDao
import com.example.torneo.Core.Data.Dao.JugadorDao
import com.example.torneo.Core.Data.Equipo
import com.example.torneo.Core.Data.Jugador
import com.example.torneo.Core.Data.Torneo
import com.example.torneo.Core.Data.TorneoDao


@Database(entities = [Torneo::class, Equipo::class, Jugador::class], version=4, exportSchema = false)
abstract class TorneoDB: RoomDatabase() {
    abstract fun torneoDao(): TorneoDao

    abstract fun equipoDao(): EquipoDao

    abstract fun jugadorDao() : JugadorDao


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