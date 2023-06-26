package com.example.torneo.Core.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.torneo.Core.Data.Entity.Torneo

/*
@Entity(tableName = "fecha_table", foreignKeys = [ForeignKey(entity = Equipo::class, parentColumns = ["id"], childColumns = ["torneoId"], onDelete = ForeignKey.CASCADE)])
data class Fecha(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,

    val numero: Int,
    val dia: String?,
    val equipo1: Equipo,
    val equipo2: Equipo,
    val golesEquipo1: Int?,
    val golesEquipo2: Int?,
    val idTorneo: Int,
    )

 */

@Entity(tableName = "fecha_table",
    foreignKeys =[
        ForeignKey(entity = Torneo::class, parentColumns = ["id"], childColumns = ["idTorneo"], onDelete = ForeignKey.CASCADE),
    ])
data class Fecha(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    @ColumnInfo(name="idTorneo") val idTorneo: Int,
    @ColumnInfo(name="numeroFecha") val numero: Int,

    )