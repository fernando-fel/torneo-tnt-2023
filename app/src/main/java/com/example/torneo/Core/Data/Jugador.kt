package com.example.torneo.Core.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jugador_table")
data class Jugador(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val dni: Int?,
    val nombre: String,
    val apellido: String?,
    val edad: Int?)