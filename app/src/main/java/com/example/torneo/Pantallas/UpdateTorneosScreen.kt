package com.example.torneo.Pantallas

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.torneo.Components.UpdateTorneoContent
import com.example.torneo.Components.UpdateTorneoTopBar
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.TorneoViewModel.TorneosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpdateTorneoScreen(
    viewModel: TorneosViewModel = hiltViewModel(),
    torneoId:Int,
    navigateBack: () ->Unit
){
    LaunchedEffect(Unit){
        viewModel.getTorneo(torneoId)
    }
    var torneo by remember { mutableStateOf<Torneo?>(null) }
    LaunchedEffect(torneoId) {
        torneo = viewModel.getTorneo(torneoId) // Asegúrate de que esto sea una función suspendida
    }

    Scaffold(
        topBar = {
            UpdateTorneoTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            torneo?.let {
                UpdateTorneoContent(
                    padding = padding,
                    torneo = it,
                    updateNombre = { nombre ->
                        viewModel.updateNombre(nombre)
                    },
                    updateEstado = { estado ->
                        viewModel.updateEstado(estado)
                    },
                    updateTorneo = { torneo ->
                        viewModel.updateTorneo(torneo)
                    },
                    navigateBack = navigateBack
                )
            }
        }
    )
}
