package com.example.torneo.Core.BaseDeDatos

import android.util.Log
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.Core.Data.Entity.Torneo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun sincronizar_torneos(db_firebase: FirebaseFirestore, db_local: TorneoDB) {

    val scope = CoroutineScope(Dispatchers.IO)
    val dao = db_local.torneoDao()

    db_firebase.collection("torneos")
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

    db_firebase.collection("persona")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val persona = Persona(
                    id = document.id.toInt(),
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

    db_firebase.collection("equipos")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                val equipo = Equipo(
                    id = document.id.toInt(),
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



fun sincronizar_db(db_remota: FirebaseFirestore, db_local: TorneoDB) {
    sincronizar_torneos(db_remota, db_local)
    sincronizar_personas(db_remota, db_local)
    sincronizar_equipos(db_remota, db_local)
}
