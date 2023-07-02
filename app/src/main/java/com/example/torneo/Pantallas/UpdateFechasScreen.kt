package com.example.torneo.Pantallas

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.torneo.Components.UpdateFechasContent
import com.example.torneo.Components.UpdateTorneoContent
import com.example.torneo.Components.UpdateTorneoTopBar
import com.example.torneo.TorneoViewModel.FechasViewModel
import com.example.torneo.TorneoViewModel.TorneosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpdateFechasScreen(
    viewModel: FechasViewModel = hiltViewModel(),
    fechasId:Int,
    navigateBack: () ->Unit
){
    LaunchedEffect(Unit){
        viewModel.getFecha(fechasId)
    }
    Scaffold(
        topBar = {
            UpdateTorneoTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            UpdateFechasContent(
                padding = padding,
                fecha = viewModel.fecha,
                updateNumero = { numero ->
                    viewModel.updateNumero(numero)
                },
                updateEstado = { estado ->
                    viewModel.updateEstado(estado)
                },
                updateFecha = { fecha ->
                    viewModel.updateFecha(fecha)
                },
                navigateBack = navigateBack
            )

        }
    )
}
