package com.example.torneo.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsActions.OnClick
import androidx.compose.ui.unit.dp
import com.example.torneo.Core.Data.Torneo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TorneoCard(
    torneo: Torneo,
    deleteTorneo: ()-> Unit,
    navigateToUpdateTorneoScreen: (torneoId: Int)-> Unit
){
        Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation() ,
            onClick = {
            navigateToUpdateTorneoScreen(torneo.id)
        }

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column() {
                Text(text = torneo.nombre)
                Text((torneo.tipo).toString())
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            DeleteIcon(
                deleteTorneo = deleteTorneo
            )
        }
    }
}