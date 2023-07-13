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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EquiposViewModel @Inject constructor(
    private val repo: EquipoRepository
): ViewModel()
{
    var equipo by mutableStateOf(Equipo(0,""))
    var openDialog by mutableStateOf(false)
    val equipos = repo.getEquipoFromRoom()

    fun addEquipo(equipo: Equipo) = viewModelScope.launch(Dispatchers.IO)
    {
        val db = Firebase.firestore
        repo.addEquipoToRoom(equipo)
        db.collection("Equipo").document(equipo.id.toString())
            .set(equipo)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
    fun closeDialog(){
        openDialog = false
    }
    fun openDialog(){
        openDialog = true
    }
    fun deleteEquipo(equipo: Equipo) =
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteEquipo(equipo)
        }

    fun  updateNombre(nombre:String){
        equipo = equipo.copy(
            nombre= nombre
        )
    }

    fun updateEquipo(equipo :Equipo) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = Firebase.firestore
            repo.updateEquipo(equipo)
            db.collection("Equipo").document(equipo.id.toString())
                .set(equipo)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }
    fun getEquipo(id: Int) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        equipo = repo.getEquipo(id)
    }
}