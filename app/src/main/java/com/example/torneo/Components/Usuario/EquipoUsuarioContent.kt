package com.example.torneo.Components.Usuario

import com.example.torneo.Core.Data.repository.Equipos
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.torneo.Core.Data.Entity.Equipo

@Composable
fun EquipoUsuarioContent(
    padding: PaddingValues,
    equipos: Equipos,
    navegarAPartidosDeEquipo: (equipoId: Int) -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Buscar Equipos") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        val filteredEquipos = equipos.filter {
            it.nombre.contains(searchQuery.value, ignoreCase = true)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredEquipos) { equipo ->
                EquipoUsuarioCard(
                    equipo = equipo,
                    navegarAPartidosDeEquipo = navegarAPartidosDeEquipo
                )
            }
        }
    }
}
