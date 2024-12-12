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
import com.example.torneo.Core.Data.repository.FechaRepository
import com.example.torneo.Core.Data.repository.PartidoRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PartidosViewModel @Inject constructor(
    private val partidoRepo: PartidoRepository,
    private val fechaRepo: FechaRepository
) : ViewModel() {
    var partidos = partidoRepo.getAllPartidos()
    var openDialog by mutableStateOf(false)
    val tiempoActualPartido = MutableStateFlow(0L) // Tiempo actual del partido

    // Estado que mantendrá los partidos de hoy
    var partidosHoyFirebase = mutableStateListOf<PartidoConTiempo>()

    data class PartidoConTiempo(
        val partido: Partido,
        val tiempoTrascurrido: String?
    )

    fun loadPartidosDeHoyDesdeFirebase() {
        val db = FirebaseFirestore.getInstance()
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaHoy = formatter.format(Date())

        db.collection("partidos")
            .whereEqualTo("dia", fechaHoy)
            .get()
            .addOnSuccessListener { documents ->
                val partidos = documents.mapNotNull { document ->
                    var partido = document.toObject(Partido::class.java).apply {
                        id = document.id.toInt()
                        golLocal = (document.getLong("golLocal")?.toInt() ?: 0).toString()
                        golVisitante = (document.getLong("golVisitante")?.toInt() ?: 0).toString()
                    }
                    val tiempoTrascurrido = (((document.getString("tiempoTrascurrido")?.toInt() ?:0) /60) /1000).toString()
                    PartidoConTiempo(partido, tiempoTrascurrido)
                }
                partidosHoyFirebase.clear()
                partidosHoyFirebase.addAll(partidos)
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents: ", exception)
            }
    }


    // Función que obtiene los partidos de hoy desde Firebase (solo si se necesita)
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadPartidosDeHoy(): Flow<List<PartidoDao.PartidoConDetalles>> {
        return partidoRepo.getPartidosDeHoyDetalle() // Usamos Room para obtener los partidos de hoy
    }

    // Funciones para manejar la base de datos de Firebase
    fun addPartido(partido: Partido) = viewModelScope.launch(Dispatchers.IO) {
        val db = Firebase.firestore
        partidoRepo.addPartido(partido)
        var cantidad = partidoRepo.getCountEquipos()
        var partido2 = partido.copy(id = cantidad)
        db.collection("Partidos").document(partido2.id.toString())
            .set(partido2)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error writing document", e)
            }
    }

    fun closeDialog() {
        openDialog = false
    }

    fun openDialog() {
        openDialog = true
    }

    fun deletePartido(partido: Partido) = viewModelScope.launch(Dispatchers.IO) {
        partidoRepo.deletePartido(partido)
    }

    fun updatePartido(partido: Partido,tiempo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = Firebase.firestore

            db.collection("partidos").document(partido.id.toString())
                .set(partido)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot successfully updated!")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error updating document", e)
                }
            // Luego, añade el nuevo campo al documento
            db.collection("partidos").document(partido.id.toString())
                .update("tiempoTrascurrido", tiempo)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot successfully updated with nuevoCampo!")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error updating document with nuevoCampo", e)
                }
            partidoRepo.updatePartido(partido)
        }
    }
    fun startAutoRefresh() {
        viewModelScope.launch {
            while (true) {
                delay(5000) // Espera de 10 segundos
                loadPartidosDeHoyDesdeFirebase() // Recargar partidos
            }
        }
    }
    suspend fun getTorneoIdByFecha(fechaId: Int): String? {
        val fecha = fechaRepo.getFecha(fechaId)
        return fecha?.idTorneo // Asegúrate de que el campo sea correcto
    }
}