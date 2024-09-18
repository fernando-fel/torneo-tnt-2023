package com.example.torneo.Pantallas

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.torneo.Components.UpdateTorneoContent
import com.example.torneo.Components.UpdateTorneoTopBar
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
    Scaffold(
        topBar = {
            UpdateTorneoTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            UpdateTorneoContent(
                padding = padding,
                torneo = viewModel.torneo,
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
    )
}
