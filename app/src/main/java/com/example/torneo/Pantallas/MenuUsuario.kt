package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Mapas.myMarket
import com.example.torneo.TorneoViewModel.EquiposViewModel
import com.example.torneo.TorneoViewModel.FechasViewModel
import com.example.torneo.TorneoViewModel.PartidosViewModel
import com.example.torneo.TorneoViewModel.TorneosViewModel

data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val badge: Int? = null
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuUsuario(navController: NavHostController) {
    val scrollState = rememberScrollState()


    Scaffold(
        topBar = {
            CustomTopAppBar(navController, "Menu Principal", true)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Perfil del Usuario
            UserProfileCard()

            // Menú Principal
            MainMenuSection(navController)

            // Mapa
            MapSection()
        }
    }
}

@Composable
fun UserProfileCard() {
    val viewModel: PartidosViewModel = hiltViewModel()
    val viewModel2 : TorneosViewModel = hiltViewModel()
    val viewModel3 : EquiposViewModel = hiltViewModel()
    val cantEquipos = (viewModel3.equipos.collectAsState(initial = emptyList()).value).count()
    val cantTorneos = (viewModel2.torneos.collectAsState(initial = emptyList()).value).count()
    val cantPartidos = (viewModel.partidos.collectAsState(initial = emptyList()).value).count()

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Surface(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Estadísticas del usuario
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("Torneos", cantTorneos.toString())
                StatItem("Equipos", cantEquipos.toString())
                StatItem("Partidos", cantPartidos.toString())
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainMenuSection(navController: NavHostController) {
    val viewModel: PartidosViewModel = hiltViewModel()
    val cantPartidosHoy = (viewModel.loadPartidosDeHoy().collectAsState(initial = emptyList()).value).count()
    val menuItems = listOf(
        MenuItem(
            title = "Torneos",
            icon = Icons.Default.EmojiEvents,
            route = Routes.TorneosUsuarioScreen.route
        ),
        MenuItem(
            title = "Equipos",
            icon = Icons.Default.Group,
            route = Routes.EquiposUsuarioScreen.route
        ),
        MenuItem(
            title = "En Vivo",
            icon = Icons.Default.LiveTv,
            route = Routes.PartidosEnVivoScreen.route,
            badge = cantPartidosHoy
        )
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        menuItems.forEach { item ->
            MenuButton(
                title = item.title,
                icon = item.icon,
                badge = item.badge,
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}

@Composable
fun MenuButton(
    title: String,
    icon: ImageVector,
    badge: Int? = null,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            badge?.let {
                Badge(
                    containerColor = MaterialTheme.colorScheme.error
                ) {
                    Text(
                        text = it.toString(),
                        color = MaterialTheme.colorScheme.onError,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
fun MapSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        myMarket()
    }
}