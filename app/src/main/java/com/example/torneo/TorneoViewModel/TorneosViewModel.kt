package com.example.torneo.TorneoViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torneo.Core.Constantes.Companion.NO_VALUE
import com.example.torneo.Core.Data.Torneo
import com.example.torneo.Core.Data.repository.TorneoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TorneosViewModel @Inject constructor(
    private val repo: TorneoRepository
): ViewModel()
{
    var torneo by mutableStateOf(Torneo(0,NO_VALUE,2023, NO_VALUE))
    var openDialog by mutableStateOf(false)
    val torneos = repo.getAllTorneos()
    fun addTorneo(torneo: Torneo) = viewModelScope.launch(Dispatchers.IO)
    {
        repo.addTorneo(torneo)
    }
    fun closeDialog(){
        openDialog = false
}
    fun openDialog(){
        openDialog = true
    }
    fun deleteTorneo(torneo: Torneo) =
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteTorneo(torneo)
        }

    fun  updateNombre(nombre:String){
        torneo = torneo.copy(
            nombre= nombre
        )
    }
    fun  updateTipo(tipo: String){
        torneo = torneo.copy(
            tipo = tipo
        )
    }
    fun updateTorneo(torneo : Torneo) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateTorneo(torneo)
        }
    }
    fun getTorneo(id: Int) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        torneo = repo.getTorneo(id)
    }
}