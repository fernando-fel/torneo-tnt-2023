package com.example.torneo.Core.Data.Entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.torneo.Core.Data.Equipo


@Entity(tableName = "persona_table")
data class Persona(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "idPersona") val idPersona: String,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "pass") val pass: String,
    @ColumnInfo(name = "rol") val rol: String,
)
@Entity(tableName = "torneo_table",)
data class Torneo (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "idTorneo") val idTorneo: String,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "fechaInicio") val fechaInicio: String,
    @ColumnInfo(name = "fechaFin") val fechaFin: String,
    @ColumnInfo(name = "ubicacion") val ubicacion: String,
    @ColumnInfo(name = "precio") val precio: String,
    @ColumnInfo(name = "estado") var estado: String,
)


@Entity(tableName = "fecha_table",
    foreignKeys =[
        ForeignKey(entity = Torneo::class, parentColumns = ["id"], childColumns = ["idTorneo"], onDelete = ForeignKey.CASCADE)
    ],
    indices=[Index("idTorneo")]
)
data class Fecha(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name= "idTorneo") val idTorneo: Int,
    @ColumnInfo(name= "numeroFecha") val numero: String,
    @ColumnInfo(name= "estado") val estado: String,

)

@Entity(tableName = "equipo_table",)
data class Equipo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String,
)

@Entity(tableName = "partido_table",
    foreignKeys = [
        ForeignKey(entity = Fecha::class, parentColumns = ["id"], childColumns = ["idFecha"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Equipo::class, parentColumns = ["id"], childColumns = ["idLocal"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Equipo::class, parentColumns = ["id"], childColumns = ["idVisitante"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Persona::class, parentColumns = ["id"], childColumns = ["idPersona"], onDelete = ForeignKey.CASCADE),
    ],
    indices=[Index("idFecha"), Index("idLocal"), Index("idVisitante"), Index("idPersona")]
)
data class Partido(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "idFecha") val idFecha: String = "",
    @ColumnInfo(name = "hora") val hora: String = "",
    @ColumnInfo(name = "dia") val dia: String = "",
    @ColumnInfo(name = "numCancha") val numCancha: String= "",
    @ColumnInfo(name = "idLocal") val idLocal: String= "",
    @ColumnInfo(name = "idVisitante") val idVisitante: String= "",
    @ColumnInfo(name = "golLocal") val golLocal: Int = 0,
    @ColumnInfo(name = "golVisitante") val golVisitante: Int= 0,
    @ColumnInfo(name = "estado") val estado: String = "",
    @ColumnInfo(name = "resultado") val resultado: String = "",
    @ColumnInfo(name = "idPersona") val idPersona: String = "",
)
@Entity(tableName = "torneo_equipo")
data class TorneoEquipo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "torneoId") val torneoId: Int,  // Este sigue siendo Int ya que se genera automáticamente
    @ColumnInfo(name = "equipoId") val equipoId: Int  // Este también sigue siendo Int
)