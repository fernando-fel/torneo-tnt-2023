package com.example.torneo.TorneoViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.repository.EquipoRepository
import com.example.torneo.Core.Data.repository.FechaRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FechasViewModel @Inject constructor(
    private val repo: FechaRepository,
): ViewModel()
{
    var fecha by mutableStateOf(Fecha(id = 0, idTorneo = "1", numero = "0",estado = "programado"))
    var openDialog by mutableStateOf(false)
    val fechas = repo.getAllFechas()
    fun addFecha(fecha: Fecha) = viewModelScope.launch(Dispatchers.IO)
    {
        val db = Firebase.firestore
        repo.addFecha(fecha)
        db.collection("Fecha").document(fecha.id.toString())
            .set(fecha)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

        //fecha.idTorneo = idTorneo

    }
    fun closeDialog(){
        openDialog = false
    }
    fun openDialog(){
        openDialog = true
    }
    fun deleteFecha(fecha:Fecha) =
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteFecha(fecha)
        }

    fun updateEstado(estado : String){
        fecha = fecha.copy(
            estado = estado
        )
    }
    fun updateNumero(numero: String){
        fecha = fecha.copy(
            numero = numero
        )
    }

    fun updateFecha(fecha: Fecha) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = Firebase.firestore
            repo.updateFecha(fecha)
            db.collection("Fecha").document(fecha.id.toString())
                .set(fecha)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }
    fun getFecha(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        fecha = repo.getFecha(id)
    }
}