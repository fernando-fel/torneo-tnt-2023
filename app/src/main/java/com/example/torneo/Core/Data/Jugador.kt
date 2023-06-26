package com.example.torneo.Core.Data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

/*@Entity(tableName = "jugador_table", foreignKeys = [ForeignKey(entity = Equipo::class, parentColumns = ["id"], childColumns = ["equipoId"], onDelete = ForeignKey.CASCADE)])
data class Jugador(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val dni: Int?,
    val nombre: String,
    val apellido: String?,
    val edad: Int?,
    val equipoId: Int)*/


/*
@Entity(tableName = "jugador_table")
data class Jugador(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val equipoPerteneciente: Int,
    val dni: Int?,
    val nombre: String,
    val apellido: String?,
    val edad: Int?,
    val equipoId: Int)
*/

@Entity(tableName = "jugador_table")
data class Jugador (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val usuario: String,
    val password: String,
    val esJuez: Boolean = false
)

