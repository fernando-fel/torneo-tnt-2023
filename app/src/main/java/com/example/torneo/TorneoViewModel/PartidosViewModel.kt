package com.example.torneo.TorneoViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Partido

import com.example.torneo.Core.Data.repository.PartidoRepository
import com.example.torneo.Core.Data.repository.Partidos

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartidosViewModel @Inject constructor(
    private val repo: PartidoRepository
): ViewModel()
{

    var partido by mutableStateOf(Partido(id = 0, estado = "", dia = "", golLocal = 0, golVisitante = 0,
                                        hora = "", idFecha = 0, idLocal = 0, idVisitante = 0, numCancha = "",
                                         resultado = ""))
    var openDialog by mutableStateOf(false)
    val partidos = repo.getAllPartidos()
    fun addPartido(partido: Partido) = viewModelScope.launch(Dispatchers.IO)
    {
        repo.addPartido(partido)
    }
    fun closeDialog(){
        openDialog = false
    }
    fun openDialog(){
        openDialog = true
    }
    fun deletePartido(partido:Partido) =
        viewModelScope.launch(Dispatchers.IO){
            repo.deletePartido(partido)
        }

    /*    fun  updateNombre(nombre:String){
            equipo = equipo.copy(
                nombre= nombre
            )
        }*/

    fun updatepartido(partido: Partido) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updatePartido(partido)
        }
    }
    fun getPartido(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        partido = repo.getPartido(id)
    }
}