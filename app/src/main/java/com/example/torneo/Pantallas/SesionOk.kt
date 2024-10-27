package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.torneo.Mapas.myMarket
import kotlin.random.Random

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SesionOk(navController: NavHostController) {
    ScaffoldWithTopBarSesionOk(navController)
}

data class MenuOption(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarSesionOk(navController: NavHostController) {
    val menuOptions = listOf(
        MenuOption("Torneos", Icons.Default.EmojiEvents, Routes.TorneosScreen.route),
        MenuOption("Equipos", Icons.Default.Group, Routes.EquiposScreen.route),
        MenuOption("Fechas", Icons.Default.DateRange, Routes.FechasScreen.route),
        MenuOption("Fixture", Icons.Default.Schedule, Routes.Fixture.route),
        MenuOption("Personas Inscriptas", Icons.Default.Person, Routes.ListadoDePersonas.route)
    )

    Scaffold(
        topBar = {
            CustomTopAppBar(navController, "Bienvenido", true)
        },
        content = { padding ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Sección de publicidad mejorada
                    PublicidadCarousel()

                    // Menú de opciones
                    MenuGrid(menuOptions, navController)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mapa
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        myMarket()
                    }
                }
            }
        }
    )
}

@Composable
fun PublicidadCarousel() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        val scrollState = rememberScrollState()

        Row(
            Modifier
                .horizontalScroll(scrollState)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(10) {
                Card(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(randomColor().copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Publicidad ${it + 1}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MenuGrid(options: List<MenuOption>, navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            MenuButton(
                title = option.title,
                icon = option.icon,
                onClick = { navController.navigate(option.route) }
            )
        }
    }
}

@Composable
fun MenuButton(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

fun randomColor() = Color(Random.nextLong(0xFFFFFFFF))