package com.example.torneo.Components.Usuario

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.torneo.Pantallas.MenuItem
import com.example.torneo.Pantallas.Routes

@Composable
fun MenuBottomBar(navController: NavHostController){
    NavigationBar {
        val menuItems = listOf(
            MenuItem(
                title = "Inicio",
                icon = Icons.Default.Home,
                route = Routes.MenuUsuario.route
            ),
            MenuItem(
                title = "Torneos",
                icon = Icons.Default.EmojiEvents,
                route = Routes.TorneosUsuarioScreen.route
            ),
            MenuItem(
                title = "En Vivo",
                icon = Icons.Default.LiveTv,
                route = Routes.PartidosEnVivoScreen.route
            )
        )
        val currentRoute = navController.currentDestination?.route ?: menuItems.first().route
        menuItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = MaterialTheme.colorScheme.primary
                    ) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}