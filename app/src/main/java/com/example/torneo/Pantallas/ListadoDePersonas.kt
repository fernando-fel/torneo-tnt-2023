package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card

import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.torneo.Core.Data.Entity.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

import com.example.torneo.Core.Data.Dao.PersonaDao


@ExperimentalMaterial3Api
@Composable
fun ListadoDePersonas(
    navController: NavHostController,
    personaDao: PersonaDao
) {
    val mostrarDialog = remember { mutableStateOf(false) }
    val personasList = remember { mutableStateListOf<Persona>() }
    val nuevoRol = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val personas: List<Persona> = personaDao.getPersonaList()
            personasList.addAll(personas)
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(navController, "Listado de Personas Inscriptas", true)
        },
        content = { padding ->
            Column (
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                personasList.forEachIndexed { index, persona ->
                    var nuevoRol = remember { mutableStateOf(persona.rol) }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp,4.dp),
                        elevation = CardDefaults.cardElevation(),
                        onClick = {  nuevoRol.value = persona.rol }
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text(
                                text = "Nombre: ${persona.nombre}",
                                style = TextStyle(fontWeight = FontWeight.Bold)
                            )
                            Text(text = "Rol: ${persona.rol}")
                            Text(text = "Usuario: ${persona.username}")
                            Text(text = "Contraseña: ${persona.pass}")

                            Spacer(modifier = Modifier.height(8.dp))
                            val coroutineScope = rememberCoroutineScope()
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextField(
                                    value = nuevoRol.value,
                                    onValueChange = { nuevoRol.value = it },
                                    label = { Text("Nuevo rol "+persona.rol ) },
                                    modifier = Modifier.weight(1f)
                                )
                                Button(
                                    onClick = {
                                        coroutineScope.launch{
                                                val personaActualizada: Persona = persona.copy(rol = nuevoRol.value)
                                                personasList[index] = personaActualizada
                                                // Actualizar la persona en la base de datos

                                                personaDao.updatePersona(personaActualizada)
                                            }
                                        }

                                ) {
                                    Text("Guardar")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}