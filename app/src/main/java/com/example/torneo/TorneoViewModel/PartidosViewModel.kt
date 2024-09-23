package com.example.torneo.TorneoViewModel

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torneo.Core.Data.Dao.PartidoDao
import com.example.torneo.Core.Data.Entity.Partido

import com.example.torneo.Core.Data.repository.PartidoRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PartidosViewModel @Inject constructor(
    private val repo: PartidoRepository
): ViewModel() {

    var partido by mutableStateOf(
        Partido(
            id = 0, estado = "", dia = "", golLocal = 0, golVisitante = 0,
            hora = "", idFecha = "0", idLocal = "0", idVisitante = "0", numCancha = "",
            resultado = "", idPersona = ""
        )
    )
    var openDialog by mutableStateOf(false)
    val partidos = repo.getAllPartidos()
    val partidosHoy = mutableStateListOf<Partido>()

    fun addPartido(partido: Partido) = viewModelScope.launch(Dispatchers.IO)
    {
        repo.addPartido(partido)
        val db = Firebase.firestore
        db.collection("Partido").document(partido.id.toString())
            .set(partido)
            .addOnSuccessListener {
                Log.d(
                    ContentValues.TAG,
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
    }

    fun closeDialog() {
        openDialog = false
    }

    fun openDialog() {
        openDialog = true
    }

    fun deletePartido(partido: Partido) =
        viewModelScope.launch(Dispatchers.IO) {
            repo.deletePartido(partido)
        }

    /*    fun  updateNombre(nombre:String){
            equipo = equipo.copy(
                nombre= nombre
            )
        }*/

    fun updatepartido(partido: Partido) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = Firebase.firestore
            db.collection("Partido").document(partido.id.toString())
                .set(partido)
                .addOnSuccessListener {
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }

            repo.updatePartido(partido)

        }
    }

    fun getPartido(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        partido = repo.getPartido(id)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun loadPartidosDeHoy(): Flow<List<PartidoDao.PartidoConDetalles>> {
        return repo.getPartidosDeHoyDetalle()
    }
}

