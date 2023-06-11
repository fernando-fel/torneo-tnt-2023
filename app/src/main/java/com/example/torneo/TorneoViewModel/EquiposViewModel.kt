package com.example.torneo.TorneoViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torneo.Core.Constantes.Companion.NO_VALUE
import com.example.torneo.Core.Data.Equipo
import com.example.torneo.Core.Data.Torneo
import com.example.torneo.Core.Data.repository.EquipoRepository
import com.example.torneo.Core.Data.repository.TorneoRepository
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
        repo.addEquipoToRoom(equipo)
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
            repo.updateEquipo(equipo)
        }
    }
    fun getEquipo(id: Int) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        equipo = repo.getEquipo(id)
    }
}