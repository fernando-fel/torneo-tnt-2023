import android.util.Log
import com.example.torneo.Core.BaseDeDatos.TorneoDB
import com.example.torneo.Core.Data.Entity.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    Log.d("Entrando a sicronizar Torneo","")
    val dao = db_local.torneoDao()
    val scope = CoroutineScope(Dispatchers.IO)

    db_firebase.collection("Torneos").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val torneo = Torneo(
                //id = document.getString("id")?.toIntOrNull()?: 0,
                id= document.getLong("id")?.toInt()?: 0,
                idTorneo = document.getString("idTorneo").toString(),
                nombre = document.getString("nombre") ?: "",
                fechaInicio = document.getString("fechaInicio") ?: "",
                fechaFin = document.getString("fechaFin") ?: "",
                ubicacion = document.getString("ubicacion") ?: "",
                precio = document.getString("precio") ?: "",
                estado = document.getString("estado") ?: ""
            )

            scope.launch {
                val existingTorneo = dao.getTorneo(id = torneo.idTorneo)
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
    Log.d("Entrando a sicronizar Persoa","")
    val dao = db_local.personaDao()
    val scope = CoroutineScope(Dispatchers.IO)

    db_firebase.collection("Personas").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val persona = Persona(
                //id = document.getString("id")?.toIntOrNull()?: 0,
                id = document.getLong("id")?.toInt()?: 0,
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
                    dao.addPersona(persona)
                }
            }
        }
    }.addOnFailureListener { exception ->
        Log.w("TAG", "Error getting documents from personas.", exception)
    }
}

private fun sincronizarFechas(db_firebase: FirebaseFirestore, db_local: TorneoDB) {
    Log.d("Entrando a sicronizar Fecha","")
    val dao = db_local.fechaDao()
    val torneoDao = db_local.torneoDao()
    val scope = CoroutineScope(Dispatchers.IO)

    db_firebase.collection("fechas").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val idTorneo = document.getString("idTorneo") ?: ""
            Log.d("idTorneo", idTorneo)

            scope.launch {
                val torneo = torneoDao.getTorneo(id = idTorneo.toInt().toString())
                Log.d("TAG", "Sincronizando fecha con este torneo con ID: ${torneo}")
                if (torneo != null) {  // Asegúrate de que el torneo existe antes de insertar la fecha
                    val fecha = Fecha(
                        id = document.getLong("id")?.toInt()?: 0,
                        idTorneo = document.getString("idTorneo")?: "",
                        numero = document.getString("numero") ?: "",
                        estado = document.getString("estado") ?: ""
                    )

                    val existingFecha = dao.getFecha(fecha.id.toString())
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
    Log.d("Entrando a sicronizar Equipo","")
    val dao = db_local.equipoDao()
    val scope = CoroutineScope(Dispatchers.IO)

    db_firebase.collection("Equipos").get().addOnSuccessListener { result ->
        result.forEach { document ->
            val equipo = Equipo(
                //id = document.getString("id")?.toIntOrNull()?: 0, // Ajusta el ID según sea necesario
                id =  document.getLong("id")?.toInt()?: 0,
                nombre = document.getString("nombre") ?: ""
            )

            scope.launch {
                val existingEquipo = dao.getEquipo(equipo.id.toString())
                Log.d("TAG", "Sincronizando equipo con ID: ${equipo.id}")
                if (existingEquipo != null) {
                    if (existingEquipo != equipo) {
                        dao.updateEquipo(equipo)
                    }
                } else {
                    Log.d("Tag", "Sincronizado")
                    dao.insertEquipo(equipo)
                }
            }
        }
    }.addOnFailureListener { exception ->
        Log.w("TAG", "Error getting documents from equipos.", exception)
    }
}
private fun sincronizarPartidos(db_firebase: FirebaseFirestore, db_local: TorneoDB) {
    Log.d("Entrando a sicronizar Partido","")
    val partidoDao = db_local.partidoDao()
    val equipoDao = db_local.equipoDao()
    val fechaDao = db_local.fechaDao()
    val scope = CoroutineScope(Dispatchers.IO)

    db_firebase.collection("Partidos").get().addOnSuccessListener { result ->
        result.forEach { document ->

            val idFecha = document.getString("idFecha") ?: ""
            val idLocal = document.getString("idLocal") ?: ""
            val idVisitante = document.getString("idVisitante") ?: ""
            //val idFecha = document.getLong("idFecha")?.toInt()?: 0
            //val idLocal = document.getLong("idLocal")?.toInt()?: 0
            //val idVisitante = document.getLong("idVisitante")?.toInt()?: 0
            Log.d("idFecha", idFecha.toString())
            Log.d("idLocal", idLocal.toString())
            Log.d("idVisitante", idVisitante.toString())

            scope.launch {
                val fecha = fechaDao.getFecha(id = idFecha.toInt().toString())
                val local = equipoDao.getEquipo(id = idLocal.toInt().toString())
                val visitante = equipoDao.getEquipo(id = idVisitante.toInt().toString())

                if (fecha != null && local != null && visitante != null) {
                    //val golLocalString = document.getString("golLocal") ?: "0"
                    //val golVisitanteString = document.getString("golVisitante") ?: "0"
                    val golLocal = document.getLong("golLocal")?.toInt()?: 0
                    val golVisitante = document.getLong("golVisitante")?.toInt()?: 0

                    val partido = Partido(
                        //id = document.getString("id")?.toIntOrNull()?: 0,
                        id = document.getLong("id")?.toInt()?: 0,
                        idFecha = idFecha.toString(),
                        hora = document.getString("hora") ?: "",
                        dia = document.getString("dia") ?: "",
                        numCancha = document.getString("numCancha") ?: "",
                        idLocal = idLocal.toString(),
                        idVisitante = idVisitante.toString(),
                        golLocal = golLocal,
                        golVisitante = golVisitante,
                        estado = document.getString("estado") ?: "",
                        resultado = document.getString("resultado") ?: "",
                        idPersona = document.getString("idPersona") ?: ""
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
                    Log.w("TAG", "No se encontró fecha, local o visitante para idFecha: $idFecha, idLocal: $idLocal, idVisitante: $idVisitante")
                }
            }
        }
    }.addOnFailureListener { exception ->
        Log.w("TAG", "Error obteniendo documentos de partidos.", exception)
    }
}
