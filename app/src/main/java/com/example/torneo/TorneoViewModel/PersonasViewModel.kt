package com.example.torneo.TorneoViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.Core.Data.repository.PersonaRepository
import com.example.torneo.Core.Data.repository.PersonaRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PersonasViewModel @Inject constructor(
    private val repo: PersonaRepository
): ViewModel()
{
    var persona by mutableStateOf(Persona(0, 0,"","","","" ))
    var openDialog by mutableStateOf(false)
    var personas = repo.getPersonaFromRoom()

    fun addPersona(persona: Persona) = viewModelScope.launch(Dispatchers.IO)
    {
        repo.addPersonaToRoom(persona)
    }
    fun closeDialog(){
        openDialog = false
    }
    fun openDialog(){
        openDialog = true
    }
    fun deletePersona(persona: Persona) =
        viewModelScope.launch(Dispatchers.IO){
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
    fun getPersona(id: Int) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        persona = repo.getPersona(id)
    }
}