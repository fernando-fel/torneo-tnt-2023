package com.example.torneo.TorneoViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.repository.EquipoRepository
import com.example.torneo.Core.Data.repository.FechaRepository
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
    var fecha by mutableStateOf(Fecha(id = 0, idTorneo = 1, numero = 0,estado = "Creado"))
    var openDialog by mutableStateOf(false)
    val fechas = repo.getAllFechas()
    fun addFecha(fecha: Fecha) = viewModelScope.launch(Dispatchers.IO)
    {
        //fecha.idTorneo = idTorneo
        repo.addFecha(fecha)
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

/*    fun  updateNombre(nombre:String){
        equipo = equipo.copy(
            nombre= nombre
        )
    }*/

    fun updateFecha(fecha: Fecha) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateFecha(fecha)
        }
    }
    fun getFecha(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        fecha = repo.getFecha(id)
    }
}