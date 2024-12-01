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
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Core.Data.repository.EquipoRepository
import com.example.torneo.Core.Data.repository.FechaRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FechasViewModel @Inject constructor(
    private val repo: FechaRepository,
) : ViewModel() {

    var fecha by mutableStateOf(Fecha(id = 0, idTorneo = 1, numero = "0", estado = "programado"))
    var openDialog by mutableStateOf(false)
    val fechas = repo.getAllFechas().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    //val fechas = repo.getAllFechas()

//    fun addFecha(fecha: Fecha) = viewModelScope.launch(Dispatchers.IO) {
//        val db = Firebase.firestore
//        repo.addFecha(fecha)
//        db.collection("Fecha").document(fecha.id.toString())
//            .set(fecha)
//            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
//            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
//    }

    fun closeDialog() {
        openDialog = false
    }

    fun openDialog() {
        openDialog = true
    }

    fun deleteFecha(fecha: Fecha) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteFecha(fecha)
    }
    fun getFecha2(id: Int): Flow<Fecha> = flow {
        val result = repo.getFecha(id)
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun updateEstado(estado: String) {
        fecha = fecha.copy(estado = estado)
    }

    fun updateNumero(numero: String) {
        fecha = fecha.copy(numero = numero)
    }

    fun updateFecha(fecha: Fecha) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = Firebase.firestore
            repo.updateFecha(fecha)
            db.collection("fechas").document(fecha.id.toString())
                .set(fecha)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }
    suspend fun getFecha3(id: Int): Fecha? {
        return withContext(Dispatchers.IO) {
            repo.getFecha(id)
        }
    }

    fun getFecha(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        fecha = repo.getFecha(id)
    }

    fun getNextFechaNumber(torneoId: String): Int {
        val fechasDeTorneo = fechas.value.filter { it.idTorneo == torneoId.toInt() }
        val maxNumber = fechasDeTorneo
            .mapNotNull { it.numero.toIntOrNull() }
            .maxOrNull() ?: 0
        Log.d("FechasViewModel", "Max number for torneoId $torneoId: $maxNumber")
        return maxNumber + 1
    }

    fun addFecha(fecha: Fecha) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("FechasViewModel", "Adding fecha: $fecha")
        try {
            val db = Firebase.firestore
            repo.addFecha(fecha)
            var cantidad = repo.getCountFechas()
            var fecha2 = fecha.copy(id = cantidad)
            db.collection("fechas").document(fecha2.id.toString())
                .set(fecha2)
                .addOnSuccessListener {
                    Log.d(
                        "FechasViewModel",
                        "Fecha added successfully: $fecha"
                    )
                }
                .addOnFailureListener { e -> Log.w("FechasViewModel", "Error adding fecha", e) }
        } catch (e: Exception) {
            Log.e("FechasViewModel", "Error adding fecha", e)
        }
    }
}