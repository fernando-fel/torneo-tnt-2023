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

    db_firebase.collection("torneos").get().addOnSuccessListener { result ->
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
                val existingTorneo = dao.getTorneo(id = torneo.idTorneo.toInt())
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

    db_firebase.collection("persona").get().addOnSuccessListener { result ->
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
    val torneoDao = db_local.torneoDao()
    val scope = CoroutineScope(Dispatchers.IO)

    db_firebase.collection("fechas").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val idTorneo = document.getString("idTorneo") ?: ""
            Log.d("idTorneo", idTorneo)

            scope.launch {
                val torneo = torneoDao.getTorneo(idTorneo.toInt())
                Log.d("TAG", "Sincronizando fecha con ID: $torneo")
                if (torneo != null) {  // Asegúrate de que el torneo existe antes de insertar la fecha
                    val fecha = Fecha(
                        id = (document.getString("id") ?: "").toInt(),
                        idTorneo = idTorneo,
                        numero = document.getString("numero") ?: "",
                        estado = document.getString("estado") ?: ""
                    )

                    val existingFecha = dao.getFecha(fecha.id)
                    if (existingFecha != null) {
                        if (existingFecha != fecha) {
                            dao.updateFecha(fecha)
                        }
                    } else {
                        dao.insertFecha(fecha)
                    }
                } else {
                    Log.w("TAG", "Torneo no encontrado para id: $idTorneo")
                }
            }
        }
    }.addOnFailureListener { exception ->
        Log.w("TAG", "Error al obtener documentos de fechas.", exception)
    }
}
private fun sincronizarEquipos(db_firebase: FirebaseFirestore, db_local: TorneoDB) {
    val dao = db_local.equipoDao()
    val scope = CoroutineScope(Dispatchers.IO)

    db_firebase.collection("Equipo").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val equipo = Equipo(
                id = document.getString("id")?.toInt() ?: 0, // Ajusta el ID según sea necesario
                nombre = document.getString("nombre") ?: ""
            )

            scope.launch {
                val existingEquipo = dao.getEquipo(equipo.id)
                Log.d("TAG", "Sincronizando equipo con ID: ${equipo.id}")
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

    db_firebase.collection("partidos").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val idFecha = document.getString("idFecha") ?: ""
            Log.d("idFecha", idFecha)
            Log.d("TAG", "Sincronizando partido con IDLoca: $document.getString(idLocal")
            Log.d("TAG", "Sincronizando partido con IDVisitante: $document.getString(idVisitante")
            scope.launch {
                val dao = db_local.fechaDao()
                val fecha = dao.getFecha(idFecha.toInt())  // Mover aquí
                if (fecha != null) {  // Asegúrate de que la fecha existe antes de crear el partido
                    val golLocalString = document.getString("golLocal") ?: "0"
                    val golVisitanteString = document.getString("golVisitante") ?: "0"

                    // Verificar si las cadenas están vacías antes de convertir
                    val golLocal = if (golLocalString.isNotEmpty()) golLocalString.toInt() else 0
                    val golVisitante = if (golVisitanteString.isNotEmpty()) golVisitanteString.toInt() else 0

                    val partido = Partido(
                        idFecha = idFecha,
                        hora = document.getString("hora") ?: "",
                        dia = document.getString("dia") ?: "",
                        numCancha = document.getString("numCancha") ?: "",
                        idLocal = document.getString("idLocal") ?: "",
                        idVisitante = document.getString("idVisitante") ?: "",
                        golLocal = golLocal,  // Aquí asignamos el valor convertido
                        golVisitante = golVisitante,  // Aquí asignamos el valor convertido
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