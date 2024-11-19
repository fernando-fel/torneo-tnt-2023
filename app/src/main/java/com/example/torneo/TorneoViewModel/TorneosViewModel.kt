package com.example.torneo.TorneoViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Core.Data.Equipo

import com.example.torneo.Core.Data.repository.TorneoRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TorneosViewModel @Inject constructor(
    private val repo: TorneoRepository
) : ViewModel() {

    var torneo by mutableStateOf(Torneo(0, ubicacion = "", idTorneo = "", precio = "", fechaInicio = "", fechaFin = "", estado = "", nombre = ""))
    var openDialog by mutableStateOf(false)
    val torneos = repo.getAllTorneos()

    fun addTorneo(torneo: Torneo) = viewModelScope.launch(Dispatchers.IO) {
        val db = Firebase.firestore
        repo.addTorneo(torneo)
        var cantidad = repo.getCountTorneos()
        var torneo2 = torneo.copy(id = cantidad)
        db.collection("torneos").document(torneo2.id.toString())
            .set(torneo)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun closeDialog() {
        openDialog = false
    }

    fun openDialog() {
        openDialog = true
    }

    fun deleteTorneo(torneo: Torneo) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteTorneo(torneo)
    }

    fun getTorneos() = viewModelScope.launch(Dispatchers.IO) {
        repo.getAllTorneos()
    }
    fun updateNombre(nombre: String) {
        torneo = torneo.copy(nombre = nombre)
    }

    fun updateEstado(estado: String) {
        torneo = torneo.copy(estado = estado)
    }

    fun updateTorneo(torneo: Torneo) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = Firebase.firestore
            repo.updateTorneo(torneo)
            db.collection("torneos").document(torneo.id.toString())
                .set(torneo)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }

    suspend fun getTorneo(id: Int): Torneo? {
        return repo.getTorneo(id) // Suponiendo que esto devuelve el Torneo
    }

    suspend fun inscribirEquipos(torneoId: Int, equipos: List<Equipo>) {
        val db = Firebase.firestore
        try {
            for (equipo in equipos) {
                db.collection("Torneo").document(torneoId.toString())
                    .collection("Equipos")
                    .add(equipo)
                    .addOnSuccessListener {
                        Log.d(TAG, "Equipo ${equipo.nombre} inscrito correctamente.")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error inscribiendo equipo ${equipo.nombre}", e)
                    }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error inscribiendo equipos: ${e.message}")
        }
    }
}
