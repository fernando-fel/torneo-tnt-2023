package com.example.torneo.Components.Usuario

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.torneo.Core.Data.Entity.Fecha

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FechaUsuarioCard(
    fecha: Fecha,
    deleteFecha: ()-> Unit,
    navigateToUpdateFechaScreen: (fechaId: Int)-> Unit,
    navigateToPartidoScreen: (fechaId: Int) -> Unit,
){
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(8.dp,4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation() ,
        onClick = {
            navigateToPartidoScreen(fecha.id)
        }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column() {
                Text(
                    text="FECHA: "+fecha.numero.toString(),
                    style= TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(text ="Estado de fecha: " + fecha.estado)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}