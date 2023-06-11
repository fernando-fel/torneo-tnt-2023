package com.example.torneo.Pantallas

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.torneo.Components.UpdateEquipoContent
import com.example.torneo.Components.UpdateEquipoTopBar
import com.example.torneo.Components.UpdateTorneoContent
import com.example.torneo.Components.UpdateTorneoTopBar
import com.example.torneo.TorneoViewModel.EquiposViewModel
import com.example.torneo.TorneoViewModel.TorneosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpdateEquipoScreen(
    viewModel: EquiposViewModel = hiltViewModel(),
    equipoId:Int,
    navigateBack: () ->Unit
){
    LaunchedEffect(Unit){
        viewModel.getEquipo(equipoId)
    }
    Scaffold(
        topBar = {
            UpdateEquipoTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            UpdateEquipoContent(
                padding = padding,
                equipo = viewModel.equipo,
                updateNombre = { nombre ->
                    viewModel.updateNombre(nombre)
                },

                updateEquipo = { equipo ->
                    viewModel.updateEquipo(equipo)
                },
                navigateBack = navigateBack
            )

        }
    )
}
