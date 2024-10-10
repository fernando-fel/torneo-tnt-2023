package com.example.torneo.Core.Data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation


/*@Entity(tableName = "equipo_table")
data class Equipo(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val nombre: String,
    @Relation(
        parentColumn = "id",
        entityColumn = "equipoId"
    )
    val jugadores: List<Jugador> = emptyList()
)

@Entity(tableName = "equipo_table")
data class Equipo(
    @PrimaryKey val id: Long,
    val nombre: String,
)
 */

@Entity(tableName = "equipo_table",)
data class Equipo(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    @ColumnInfo(name = "nombre") val nombre: String,
)

