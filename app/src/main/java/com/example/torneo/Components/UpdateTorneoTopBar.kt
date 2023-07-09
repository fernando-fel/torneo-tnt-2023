package com.example.torneo.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.torneo.Core.Constantes.Companion.UPDATE_TORNEO_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTorneoTopBar(
    navigateBack: () -> Unit
){
    TopAppBar(
        title = {
            Text(text = UPDATE_TORNEO_SCREEN)
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEquipoTopBar(
    navigateBack: () -> Unit
){
    TopAppBar(
        title = {
            Text(text = "Actualizar Equipo")
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null)
            }
        }
    )
}