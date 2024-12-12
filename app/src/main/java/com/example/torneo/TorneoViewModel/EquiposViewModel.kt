package com.example.torneo.TorneoViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.repository.EquipoRepository
import com.example.torneo.Core.Data.repository.FechaRepository
import com.example.torneo.Core.Data.repository.TorneoRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EquiposViewModel @Inject constructor(
    private val repo: EquipoRepository,
    private val repo2 : TorneoRepository,
    private val repo3 : FechaRepository
) : ViewModel() {

    var equipo by mutableStateOf(Equipo(0, ""))
    var openDialog by mutableStateOf(false)
    val equipos = repo.getEquipoFromRoom()


    fun recuperarEquiposTorneo(idFecha: Int): Flow<List<Equipo>> = flow {
        val fecha = repo3.getFecha(idFecha)
        val equipos = repo2.getEquiposEnTorneo(fecha.idTorneo.toString())
        emit(equipos) // Emitir la lista de equipos
    }.flowOn(Dispatchers.IO) // Asegúrate de que se ejecute en el hilo IO


    fun addEquipo(equipo: Equipo) = viewModelScope.launch(Dispatchers.IO) {
        val db = Firebase.firestore
        repo.addEquipoToRoom(equipo)
        var cantidad = repo.getCountEquipos()
        var equipo2 = equipo.copy(id = cantidad)
        db.collection("Equipos").document(equipo2.id.toString())
            .set(equipo2)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun closeDialog() {
        openDialog = false
    }

    fun openDialog() {
        openDialog = true
    }

    fun deleteEquipo(equipo: Equipo) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteEquipo(equipo)
    }

    fun updateNombre(nombre: String) {
        equipo = equipo.copy(nombre = nombre)
    }

    fun updateEquipo(equipo: Equipo) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = Firebase.firestore
            repo.updateEquipo(equipo)
            db.collection("Equipo").document(equipo.id.toString())
                .set(equipo)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }

    fun getEquipo(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        equipo = repo.getEquipo(id)
    }
    fun getNombreEquipos(idLocal: Int, idVisitante: Int): Flow<Pair<String, String>> = flow {
        val equipoLocal = repo.getEquipo(idLocal)
        val equipoVisitante = repo.getEquipo(idVisitante)
        emit(Pair((equipoLocal?.nombre ?: "Local").toString(), (equipoVisitante?.nombre ?: "Visitante").toString()))
    }.flowOn(Dispatchers.IO) // Ejecuta en el hilo IO

    fun inscribirEquipos(selectedEquipos: List<Equipo>, torneoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            var torneo = repo2.getTorneo(id = torneoId)
            torneo.estado = "EN CURSO"
            repo2.updateTorneo(torneo)
            repo2.inscribirEquipos(torneoId, selectedEquipos)
        }
    }
}