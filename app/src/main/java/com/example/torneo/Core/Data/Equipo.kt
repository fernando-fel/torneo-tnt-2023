package com.example.torneo.Core.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipo_table")
data class Equipo(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val nombre: String)
