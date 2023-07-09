package com.example.torneo.Core.BaseDeDatos

import android.content.ContentValues.TAG
import android.util.Log
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.Core.Data.Entity.Torneo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun sincronizar_torneos(db_firebase: FirebaseFirestore, db_local: TorneoDB) {

    val scope = CoroutineScope(Dispatchers.IO)
    val dao = db_local.torneoDao()

    db_firebase.collection("Torneo")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val torneo = Torneo(
                    id = document.id.toInt(),
                    nombre = document.getString("nombre") ?: "",
                    ubicacion = document.getString("ubicacion") ?: "",
                    fechaInicio = document.getString("fechaInicio") ?: "",
                    fechaFin = document.getString("fechaFin") ?: "",
                    estado = document.getString("estado") ?:"",
                    idTorneo = document.id.toInt(),
                    precio = (document.getDouble("precio")?:"") as Double,
                )
                scope.launch {
                    withContext(Dispatchers.IO) {
                        dao.insertTorneo(torneo)
                    }
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}

fun sincronizar_personas(db_firebase: FirebaseFirestore, db_local: TorneoDB) {

    val scope = CoroutineScope(Dispatchers.IO)
    val dao = db_local.personaDao()

    db_firebase.collection("Persona")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val persona = Persona(
                    id =0,
                    nombre = document.getString("nombre") ?: "",
                    username = document.getString("username")?:"",
                    pass = document.getString("pass")?:"",
                    rol = document.getString("rol")?:"",
                    idPersona = document.id.toInt()
                )
                scope.launch {
                    withContext(Dispatchers.IO) {
                        dao.insertPersona(persona)
                    }
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}

fun sincronizar_equipos(db_firebase: FirebaseFirestore, db_local: TorneoDB) {

    val scope = CoroutineScope(Dispatchers.IO)
    val dao = db_local.equipoDao()

    db_firebase.collection("Equipo")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val equipo = Equipo(
                    id = 0,
                    nombre = document.getString("nombre") ?: "",
                )
                scope.launch {
                    withContext(Dispatchers.IO) {
                        dao.insertEquipo(equipo)
                    }
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}
fun sincronizar_fechas(db_firebase: FirebaseFirestore, db_local: TorneoDB) {

    val scope = CoroutineScope(Dispatchers.IO)
    val dao = db_local.fechaDao()

    db_firebase.collection("Fecha")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val fecha = Fecha(
                    id = 0,
                    idTorneo = document.get("idTorneo") as Int,
                    estado = document.getString("estado") ?: "",
                    numero = document.get("numero") as Int,
                )
                scope.launch {
                    withContext(Dispatchers.IO) {
                        dao.insertFecha(fecha)
                    }
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}

fun sincronizar_partidos(db_firebase: FirebaseFirestore, db_local: TorneoDB) {

    val scope = CoroutineScope(Dispatchers.IO)
    val dao = db_local.partidoDao()

    db_firebase.collection("Partidos")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val partido = Partido(
                    id = 0,
                    dia= document.getString("dia") ?: "",
                    hora = document.getString("hora") ?: "",
                    numCancha = document.getString("cacha") ?: "",
                    estado = document.getString("cacha") ?: "",
                    idLocal = document.get("idLocal") as Int,
                    idVisitante = document.get("idVisitante") as Int,
                    golLocal = document.get("golLocal") as Int,
                    golVisitante = document.get("idLocal") as Int,
                    resultado = document.getString("cacha") ?: "",
                    idFecha = document.get("idFecha") as Int,
                    idPersona = document.getString("cacha") ?: "",
                )
                scope.launch {
                    withContext(Dispatchers.IO) {
                        dao.insertPartido(partido)
                    }
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}

fun sincronizar_db(db_remota: FirebaseFirestore, db_local: TorneoDB) {
    sincronizar_torneos(db_remota, db_local)
    sincronizar_personas(db_remota, db_local)
    sincronizar_equipos(db_remota, db_local)
    sincronizar_fechas(db_remota, db_local)
    sincronizar_partidos(db_remota, db_local)

}
