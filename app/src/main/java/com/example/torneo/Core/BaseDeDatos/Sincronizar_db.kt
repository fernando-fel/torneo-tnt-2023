import android.util.Log
import com.example.torneo.Core.BaseDeDatos.TorneoDB
import com.example.torneo.Core.Data.Entity.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun sincronizar_db(db_firebase: FirebaseFirestore, db_local: TorneoDB) {
    val scope = CoroutineScope(Dispatchers.IO)

    scope.launch {
        sincronizarTorneos(db_firebase, db_local)
        sincronizarPersonas(db_firebase, db_local)
        sincronizarFechas(db_firebase, db_local)
        sincronizarEquipos(db_firebase, db_local)
        sincronizarPartidos(db_firebase, db_local)
    }
}

private fun sincronizarTorneos(db_firebase: FirebaseFirestore, db_local: TorneoDB) {
    val dao = db_local.torneoDao()
    val scope = CoroutineScope(Dispatchers.IO)

    db_firebase.collection("Torneo").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val torneo = Torneo(
                idTorneo = document.getString("idTorneo") ?: "",
                nombre = document.getString("nombre") ?: "",
                fechaInicio = document.getString("fechaInicio") ?: "",
                fechaFin = document.getString("fechaFin") ?: "",
                ubicacion = document.getString("ubicacion") ?: "",
                precio = document.getString("precio") ?: "",
                estado = document.getString("estado") ?: ""
            )

            scope.launch {
                val existingTorneo = dao.getTorneo(torneo.idTorneo.toInt())
                if (existingTorneo != null) {
                    if (existingTorneo != torneo) {
                        dao.update(torneo)
                    }
                } else {
                    dao.insertTorneo(torneo)
                }
            }
        }
    }.addOnFailureListener { exception ->
        Log.w("TAG", "Error getting documents from torneos.", exception)
    }
}

private fun sincronizarPersonas(db_firebase: FirebaseFirestore, db_local: TorneoDB) {
    val dao = db_local.personaDao()
    val scope = CoroutineScope(Dispatchers.IO)

    db_firebase.collection("Persona").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val persona = Persona(
                idPersona = document.getString("idPersona") ?: "",
                nombre = document.getString("nombre") ?: "",
                username = document.getString("username") ?: "",
                pass = document.getString("pass") ?: "",
                rol = document.getString("rol") ?: ""
            )

            scope.launch {
                val existingPersona = dao.getPersona(persona.idPersona.toInt())
                if (existingPersona != null) {
                    if (existingPersona != persona) {
                        dao.updatePersona(persona)
                    }
                } else {
                    dao.insertPersona(persona)
                }
            }
        }
    }.addOnFailureListener { exception ->
        Log.w("TAG", "Error getting documents from personas.", exception)
    }
}

private fun sincronizarFechas(db_firebase: FirebaseFirestore, db_local: TorneoDB) {
    val dao = db_local.fechaDao()
    val scope = CoroutineScope(Dispatchers.IO)


    db_firebase.collection("fechas").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val idTorneo = document.getString("idTorneo") ?: ""
            Log.d("id torneooo", idTorneo)

            val fecha = Fecha(
                idTorneo = document.getString("idTorneo") ?: "",
                numero = document.getString("numero") ?: "",
                estado = document.getString("estado") ?: ""
            )

            scope.launch {
                val existingFecha = dao.getFechaById(fecha.id)
                if (existingFecha != null) {
                    if (existingFecha != fecha) {
                        dao.updateFecha(fecha)
                    }
                } else {
                    dao.insertFecha(fecha)
                }
            }
        }
    }.addOnFailureListener { exception ->
        Log.w("TAG", "Error getting documents from fechas.", exception)
    }
}

private fun sincronizarEquipos(db_firebase: FirebaseFirestore, db_local: TorneoDB) {
    val dao = db_local.equipoDao()
    val scope = CoroutineScope(Dispatchers.IO)

    db_firebase.collection("Equipo").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val equipo = Equipo(
                nombre = document.getString("nombre") ?: ""
            )

            scope.launch {
                val existingEquipo = dao.getEquipo(equipo.id)
                if (existingEquipo != null) {
                    if (existingEquipo != equipo) {
                        dao.updateEquipo(equipo)
                    }
                } else {
                    dao.insertEquipo(equipo)
                }
            }
        }
    }.addOnFailureListener { exception ->
        Log.w("TAG", "Error getting documents from equipos.", exception)
    }
}
private fun sincronizarPartidos(db_firebase: FirebaseFirestore, db_local: TorneoDB) {
    val partidoDao = db_local.partidoDao()
    val scope = CoroutineScope(Dispatchers.IO)

    db_firebase.collection("Partidos").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val idFecha = document.getString("idFecha") ?: ""

            scope.launch {
                val dao = db_local.fechaDao()
                val fecha = dao.getFechaById(idFecha.toInt())  // Mover aquí
                if (fecha != null) {  // Asegúrate de que la fecha existe antes de crear el partido
                    val partido = Partido(
                        idFecha = idFecha,
                        hora = document.getString("hora") ?: "",
                        dia = document.getString("dia") ?: "",
                        numCancha = document.getString("numCancha") ?: "",
                        idLocal = document.getString("idLocal") ?: "",
                        idVisitante = document.getString("idVisitante") ?: "",
                        golLocal = document.getString("golLocal")?.toInt() ?: 0,
                        golVisitante = document.getString("golVisitante")?.toInt() ?: 0,
                        estado = document.getString("estado") ?: "",
                        resultado = document.getString("resultado") ?: "",
                        idPersona = document.getString("persona") ?: ""
                    )

                    val existingPartido = partidoDao.getPartido(partido.id)
                    if (existingPartido != null) {
                        if (existingPartido != partido) {
                            partidoDao.updatePartido(partido)
                        }
                    } else {
                        partidoDao.insertPartido(partido)
                    }
                } else {
                    Log.w("TAG", "Fecha no encontrada para id: $idFecha")
                }
            }
        }
    }.addOnFailureListener { exception ->
        Log.w("TAG", "Error getting documents from partidos.", exception)
    }
}