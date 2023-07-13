package com.example.torneo.TorneoViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torneo.Core.Constantes.Companion.NO_VALUE
import com.example.torneo.Core.Data.Entity.Torneo
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
): ViewModel()
{
    var torneo by mutableStateOf(Torneo(0, ubicacion = "", idTorneo = 1, precio = 1.0000, fechaInicio = "hoy", fechaFin = "mañaa", estado = "empezado", nombre = "nombre"))
    var openDialog by mutableStateOf(false)
    val torneos = repo.getAllTorneos()

    fun addTorneo(torneo: Torneo) = viewModelScope.launch(Dispatchers.IO)
    {
        val db = Firebase.firestore
        repo.addTorneo(torneo)
        db.collection("Torneo").document(torneo.id.toString())
            .set(torneo)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    }
    fun closeDialog(){
        openDialog = false
}
    fun openDialog(){
        openDialog = true
    }
    fun deleteTorneo(torneo: Torneo) =
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteTorneo(torneo)
        }

    fun  updateNombre(nombre:String){
        torneo = torneo.copy(
            nombre= nombre
        )
    }
    fun  updateUbicacion(estado: String){
        torneo = torneo.copy(
            estado = estado
        )
    }
    fun updateTorneo(torneo : Torneo) {
        viewModelScope.launch(Dispatchers.IO) {
        val db = Firebase.firestore
            repo.updateTorneo(torneo)
            db.collection("Torneo").document(torneo.id.toString())
                .set(torneo)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }
    fun getTorneo(id: Int) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        torneo = repo.getTorneo(id)
    }
}