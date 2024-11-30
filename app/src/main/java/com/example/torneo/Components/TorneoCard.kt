package com.example.torneo.Components


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun TorneoCard(
    torneo: Torneo,
    deleteTorneo: ()-> Unit,
    navigateToUpdateTorneoScreen: (torneoId: Int)-> Unit,
    navigateToFechasScreen: (torneoId: Int) -> Unit,
    navigateToInscripcionTorneoScreen: (torneoId: Int) -> Unit,
    showDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
){
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation() ,
            onClick = {
                if (torneo.estado== "En Inscripción"){
                    navigateToInscripcionTorneoScreen(torneo.id)
                }
                else{
                    navigateToFechasScreen(torneo.id)
                }
        }

    ){
        val modifier = when (torneo.estado) {
            "EN CURSO" -> {
                Modifier
                    .border(2.dp, Color.Green)
                    .fillMaxWidth()
                    .padding(12.dp)
            }
            "Finalizados" -> {
                Modifier
                    .border(2.dp, Color.Red)
                    .fillMaxWidth()
                    .padding(12.dp)
            }
            else -> {
                Modifier
                    .border(2.dp, Color.White)
                    .fillMaxWidth()
                    .padding(12.dp)
            }
        }

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (torneo.estado) {
                "EN CURSO" -> {
                    Column {
                        Text("NOMBRE DEL TORNEO: " + torneo.nombre,
                            fontWeight = FontWeight.Bold)
                        Text("Ubicacion: " + (torneo.ubicacion))
                        Text("Fecha de Finalización: " + torneo.fechaFin)
                        Text("Estado: " + torneo.estado)
                    }
                }
                "Finalizados" -> {
                    Column {
                        Text("NOMBRE DEL TORNEO: " + torneo.nombre,
                            fontWeight = FontWeight.Bold)
                        Text("Ubicación: " + (torneo.ubicacion))
                        Text("Fecha de Finalización: " + torneo.fechaFin)
                        Text("Estado: " + torneo.estado)
                    }
                }
                else -> {
                    Column {
                        Text("NOMBRE DEL TORNEO: " + torneo.nombre,
                            fontWeight = FontWeight.Bold)
                        Text("Ubicación: " + (torneo.ubicacion))
                        Text("Fecha de Inicio: "+torneo.fechaInicio)
                        Text("Fecha de Finalización: " + torneo.fechaFin)
                        Text("Precio del torneo: " + torneo.precio)
                        Text("Estado: " + torneo.estado)
                    }
                }
            }

            Spacer( modifier = Modifier.weight(1f) )
            IconButton(onClick = {navigateToUpdateTorneoScreen(torneo.id)}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Modificar Torneo" )
            }

            IconButton(onClick = { showDialog.value = true }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar Torneo" )
            }
            if (showDialog.value) {
                MostrarDialogoConfirmacionEliminar(
                    onConfirmar = deleteTorneo,
                    showDialog = showDialog
                )
            }
        }
    }
}
@Composable
fun MostrarDialogoConfirmacionEliminar(
    onConfirmar: () -> Unit,
    showDialog: MutableState<Boolean>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        text = { Text("¿Deseas eliminar?") },
        confirmButton = {
            Button(onClick = {
                onConfirmar()
                showDialog.value = false // Cierra el diálogo después de confirmar
                CoroutineScope(Dispatchers.Main).launch {
                    snackbarHostState.showSnackbar(
                        message = "Eliminado",
                        actionLabel = "Deshacer", // Opcional: botón para deshacer
                        duration = SnackbarDuration.Indefinite
                    )
                }
            }) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            Button(onClick = { showDialog.value = false }) {
                Text("Cancelar")
            }
        }
    )
    // Mostrar el Snackbar en la interfaz de usuario
    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun EquipoCard(
    equipo: Equipo,
    deleteEquipo: ()-> Unit,
    navigateToUpdateEquipoScreen: (equipoId: Int)-> Unit,
    showDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

){

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp, 8.dp, 4.dp),
        elevation = CardDefaults.cardElevation() ,
        onClick = {
            navigateToUpdateEquipoScreen(equipo.id)
        }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Image(
                painterResource(R.drawable.escudo_sinfondo),
                contentDescription = "Imagen de prueba",
                modifier = Modifier.size(80.dp,60.dp)
            )
            Spacer( modifier = Modifier.width(10.dp) )
            Text(
                text = equipo.nombre,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif
                )
            )

            Spacer( modifier = Modifier.weight(1f) )

            IconButton(onClick = {navigateToUpdateEquipoScreen(equipo.id)}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Modificar Equipo" )
            }
            IconButton(onClick = { showDialog.value = true }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar Torneo" )
            }
            if (showDialog.value) {
                MostrarDialogoConfirmacionEliminar(
                    onConfirmar = deleteEquipo,
                    showDialog = showDialog
                )
            }
        }
    }
}