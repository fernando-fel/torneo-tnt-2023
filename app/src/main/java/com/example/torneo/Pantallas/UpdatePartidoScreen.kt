package com.example.torneo.Pantallas
import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.torneo.Components.UpdatePartidoContent
import com.example.torneo.Components.UpdatePartidoTopBar

import com.example.torneo.TorneoViewModel.PartidosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpdatePartidoScreen(
    viewModel: PartidosViewModel = hiltViewModel(),
    partidoId:Int,
    navigateBack: () ->Unit
){
    LaunchedEffect(Unit){
        viewModel.getPartido(partidoId)
    }
    Scaffold(
        topBar = {
            UpdatePartidoTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            UpdatePartidoContent(
                padding = padding,
                partido = viewModel.partido,
                updateDia = { dia ->
                    viewModel.updateFecha(dia)
                },
                updateHora = { hora ->
                    viewModel.updateHora(hora)
                },
                navigateBack = navigateBack
            )

        }
    )
}
