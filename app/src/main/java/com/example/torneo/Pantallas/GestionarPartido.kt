package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.TorneoViewModel.EquiposViewModel
import com.example.torneo.TorneoViewModel.PartidosViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class ButtonState {
    Iniciar,
    PrimerTiempo,
    Entretiempo,
    SegundoTiempo,
    Fin
}

// PARTIDOS QUE GESTIONA EL ARBITRO/JUEZ DEL TORNEO
@Composable
fun GestionarPartido(
    viewModel: PartidosViewModel = hiltViewModel(),
    viewModel2: EquiposViewModel = hiltViewModel(),
    navControllerBack: NavHostController,
    partidoId: Int
) {

    val partidos by viewModel.partidos.collectAsState(initial = emptyList())
    val partido = partidos.firstOrNull { it.id == partidoId }

    partido?.let {
        var nombreLocal by remember { mutableStateOf("") }
        var nombreVisitante by remember { mutableStateOf("") }

        LaunchedEffect(it.idLocal, it.idVisitante) {
            val nombres = (viewModel2.getNombreEquipos(it.idLocal.toInt(), it.idVisitante.toInt())).first()
            nombreLocal = nombres.first
            nombreVisitante = nombres.second
        }

        Scaffold(
            topBar = {
                CustomTopAppBar(navControllerBack, "Gestión de Partido", true)
            }
        ) { padding ->
            val golLocal = remember { mutableStateOf(0) }
            val golVisitante = remember { mutableStateOf(0) }
            val stateButton = remember { mutableStateOf(ButtonState.Iniciar) }

            val tiempoTranscurrido = remember { mutableStateOf(0L) } // Tiempo en milisegundos
            val isTimerRunning =
                remember { mutableStateOf(false) } // Estado del timer (iniciado/pausado)

            LaunchedEffect(stateButton.value) {
                when (stateButton.value) {
                    ButtonState.PrimerTiempo -> {
                        isTimerRunning.value = true
                        tiempoTranscurrido.value = 0L
                        launch {
                            while (isTimerRunning.value) {
                                delay(1000L) // Actualizar cada 1 segundo (1 seg = 1000L)
                                tiempoTranscurrido.value += 1000L

                                // Enviar actualización cada minuto
                                if (tiempoTranscurrido.value % (60 * 1000L) == 0L) {
                                    val partidoUpdate =
                                        it.copy(estado = stateButton.value.toString())
                                    viewModel.updatePartido(
                                        partidoUpdate,
                                        tiempoTranscurrido.value.toString()
                                    )
                                }
                            }
                        }
                    }

                    ButtonState.Entretiempo -> {
                        isTimerRunning.value = false
                    }

                    ButtonState.SegundoTiempo -> {
                        isTimerRunning.value = true
                        // Iniciar en el minuto 45 (45 * 60 * 1000 milisegundos)
                        //tiempoTranscurrido.value = 45L * 60L * 1000L
                        //lo cambio para que inicie en cero
                        tiempoTranscurrido.value = 0
                        launch {
                            while (isTimerRunning.value) {
                                delay(1000L)
                                tiempoTranscurrido.value += 1000L

                                // Enviar actualización cada minuto
                                if (tiempoTranscurrido.value % (60 * 1000L) == 0L) {
                                    val partidoUpdate =
                                        it.copy(estado = stateButton.value.toString())
                                    viewModel.updatePartido(
                                        partidoUpdate,
                                        tiempoTranscurrido.value.toString()
                                    )
                                }
                            }
                        }
                    }

                    ButtonState.Fin -> {
                        isTimerRunning.value = false
                    }

                    else -> {}
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // card Estado del partido
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "Estado: ${stateButton.value.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                // card equipo local vs visitante
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 6.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TeamScore(
                                teamName = nombreLocal,
                                score = golLocal.value
                            )
                            Text(
                                text = "VS",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            TeamScore(
                                teamName = nombreVisitante,
                                score = golVisitante.value
                            )
                        }
                    }
                }

    Button(
        onClick = {
            stateButton.value = when (stateButton.value) {
                ButtonState.Iniciar -> ButtonState.PrimerTiempo
                ButtonState.PrimerTiempo -> ButtonState.Entretiempo
                ButtonState.Entretiempo -> ButtonState.SegundoTiempo
                ButtonState.SegundoTiempo -> ButtonState.Fin
                ButtonState.Fin -> ButtonState.Fin
            }
            val partidoUpdate = it.copy(estado = stateButton.value.toString())
            viewModel.updatePartido(partidoUpdate,tiempoTranscurrido.value.toString())

            // Si el partido finaliza, detener el tiempo
            if (stateButton.value == ButtonState.Fin) {
                isTimerRunning.value = false
            }
        },

                    enabled = stateButton.value != ButtonState.Fin,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (stateButton.value) {
                            ButtonState.Iniciar -> MaterialTheme.colorScheme.tertiary
                            ButtonState.PrimerTiempo -> MaterialTheme.colorScheme.primary
                            ButtonState.Entretiempo -> MaterialTheme.colorScheme.tertiary
                            ButtonState.SegundoTiempo -> MaterialTheme.colorScheme.primary
                            ButtonState.Fin -> MaterialTheme.colorScheme.error
                        }
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when (stateButton.value) {
                                ButtonState.Iniciar -> Icons.Default.PlayArrow
                                ButtonState.PrimerTiempo -> Icons.Default.Timer
                                ButtonState.Entretiempo -> Icons.Default.SettingsBackupRestore
                                ButtonState.SegundoTiempo -> Icons.Default.Timer
                                ButtonState.Fin -> Icons.Default.Stop
                            },
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        if ((stateButton.value == ButtonState.PrimerTiempo) || (stateButton.value == ButtonState.SegundoTiempo))
                            Text(
                                text = stateButton.value.name+" - "+formatTime(tiempoTranscurrido.value),
                                style = MaterialTheme.typography.titleMedium
                            )
                        else{
                            Text(
                                text = stateButton.value.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }

                // aca poner el tiempo de partido

                // Controles de goles
                AnimatedVisibility(
                    visible = stateButton.value == ButtonState.PrimerTiempo ||
                            stateButton.value == ButtonState.SegundoTiempo,
                    enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 500))
                ) {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Control de Goles",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                ScoreControls(
                                    teamName = nombreLocal,
                                    score = golLocal.value,
                                    onIncrement = {
                                        golLocal.value += 1
                                        val partidoUpdate = it.copy(
                                            golLocal = golLocal.value.toString(),
                                            resultado = "${golLocal.value} - ${golVisitante.value}"
                                        )
                                        viewModel.updatePartido(partidoUpdate,tiempoTranscurrido.value.toString())
                                    },
                                    onDecrement = {
                                        if (golLocal.value > 0) {
                                            golLocal.value -= 1
                                            val partidoUpdate = it.copy(
                                                golLocal = golLocal.value.toString(),
                                                resultado = "${golLocal.value} - ${golVisitante.value}"
                                            )
                                            viewModel.updatePartido(partidoUpdate,tiempoTranscurrido.value.toString())
                                        }
                                    }
                                )

                                ScoreControls(
                                    teamName = nombreVisitante,
                                    score = golVisitante.value,
                                    onIncrement = {
                                        golVisitante.value += 1
                                        val partidoUpdate = it.copy(
                                            golVisitante = golVisitante.value.toString(),
                                            resultado = "${golLocal.value} - ${golVisitante.value}"
                                        )
                                        viewModel.updatePartido(partidoUpdate,tiempoTranscurrido.value.toString())
                                    },
                                    onDecrement = {
                                        if (golVisitante.value > 0) {
                                            golVisitante.value -= 1
                                            val partidoUpdate = it.copy(
                                                golVisitante = golVisitante.value.toString(),
                                                resultado = "${golLocal.value} - ${golVisitante.value}"
                                            )
                                            viewModel.updatePartido(partidoUpdate,tiempoTranscurrido.value.toString())
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                // Cartel de FIN DE PARTIDO
                if (stateButton.value == ButtonState.Fin) {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "¡Partido Finalizado!",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TeamScore(
    teamName: String,
    score: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = teamName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = score.toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun ScoreControls(
    teamName: String,
    score: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = teamName,
            style = MaterialTheme.typography.labelMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = onDecrement,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        MaterialTheme.colorScheme.errorContainer,
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Decrementar",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            IconButton(
                onClick = onIncrement,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Incrementar",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

fun formatTime(timeInMillis: Long): String {
    val seconds = (timeInMillis / 1000) % 60
    val minutes = (timeInMillis / (1000 * 60)) % 60
    return "%02d:%02d".format(minutes, seconds)
}