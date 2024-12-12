package com.example.torneo.TorneoViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.Core.Data.repository.PersonaRepository
import com.example.torneo.Core.Data.repository.PersonaRepositoryImpl
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class PersonasViewModel @Inject constructor(
    private val repo: PersonaRepository
): ViewModel()
{
    var persona by mutableStateOf(Persona(0, "","","","","" ))
    var openDialog by mutableStateOf(false)
    var personas = repo.getAllPersonas()

    fun addPersona(persona: Persona) = viewModelScope.launch(Dispatchers.IO) {
        val db = Firebase.firestore
        try{
            repo.addPersona(persona)
            val cantidad = repo.getCountPersonas()
            val persona2 = persona.copy(id = cantidad,idPersona = cantidad.toString())
            db.collection("Personas").document(persona2.id.toString())
                .set(persona2)
                .await()
            Log.d(TAG, "DocumentSnapshot successfully written!")
        } catch (e: Exception) {
            Log.w(TAG, "Error adding persona", e)
        }
    }

    suspend fun getPersona(id: Int):Persona? {
        return withContext(Dispatchers.IO) {
            repo.getPersona(id)
        }
    }

    fun login(username: String, password: String, personasList: List<Persona>): Persona? {
        for (persona in personasList) {
            if (persona.username == username && persona.pass == password) {
                return persona
            }
        }
        return null // Devolver null si no se encuentra ninguna persona coincidente
    }

    fun closeDialog(){
        openDialog = false
    }
    fun openDialog(){
        openDialog = true
    }
    fun deletePersona(persona: Persona) = viewModelScope.launch(Dispatchers.IO){
            repo.deletePersona(persona)
        }

    fun  updateNombre(nombre:String){
        persona = persona.copy(
            nombre= nombre
        )
    }

    fun updatePersona(persona : Persona) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updatePersona(persona)
        }
    }

    suspend fun getPersonaList(): List<Persona>{
        return withContext(Dispatchers.IO){
            repo.getPersonaList()
        }
    }
}