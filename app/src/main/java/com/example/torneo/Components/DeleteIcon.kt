package com.example.torneo.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteIcon(
    deleteTorneo: () -> Unit
){
    IconButton(onClick = deleteTorneo ) {
        Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar Torneo" )
    }
}