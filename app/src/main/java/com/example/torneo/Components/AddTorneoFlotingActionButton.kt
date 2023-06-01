package com.example.torneo.Components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.torneo.Core.Constantes.Companion.ADD_TORNEO

@Composable
fun AddTorneoFlotingActionButton(
    openDialog:()-> Unit
){
    FloatingActionButton(
        onClick = openDialog,
        modifier = Modifier.background(Color.Black)
    ) {
        Icon(imageVector = Icons.Default.Add , contentDescription = ADD_TORNEO )
    }
}