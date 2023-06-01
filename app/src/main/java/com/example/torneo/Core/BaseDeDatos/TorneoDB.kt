package com.example.torneo.Core.BaseDeDatos
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.torneo.Core.Data.Torneo
import com.example.torneo.Core.Data.TorneoDao

@Database(entities = [Torneo::class], version=1, exportSchema = false)
abstract class TorneoDB: RoomDatabase() {
    abstract fun torneoDao(): TorneoDao


}




/*
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