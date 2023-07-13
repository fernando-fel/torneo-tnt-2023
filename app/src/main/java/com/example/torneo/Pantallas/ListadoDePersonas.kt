package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import kotlinx.coroutines.launch

import com.example.torneo.Core.Data.Dao.PersonaDao
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@ExperimentalMaterial3Api
@Composable
fun ListadoDePersonas(
    navController: NavHostController,
    //trabajar PersonaRepository
    personaDao: PersonaDao
) {
    val mostrarDialog = remember { mutableStateOf(false) }
    val personasList = remember { mutableStateListOf<Persona>() }

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
                        onClick = {
                            //nuevoRol.value = persona.rol
                            mostrarDialog.value= true
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text(
                                text = "NOMBRE: ${persona.nombre}",
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "Rol: ${persona.rol}")
                            Text(text = "Usuario: ${persona.username}")
                            Text(text = "Contraseña: ${persona.pass}")
                        }
                    }
                    if (mostrarDialog.value) {
                        val coroutineScope = rememberCoroutineScope()
                        AlertDialog(
                            onDismissRequest = { mostrarDialog.value = false },
                            title = { Text("Modificar rol") },
                            text = {
                                // Campo de texto editable para ingresar el nuevo rol
                                TextField(
                                    label = {
                                        Text(
                                            text="Cambiar rol de "+persona.nombre )},
                                    singleLine = true,
                                    value = nuevoRol.value,
                                    onValueChange = { nuevoRol.value = it },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        mostrarDialog.value = false // Cerrar el cuadro de diálogo
                                            // Crear una nueva instancia de Persona con el nuevo valor de rol
                                        coroutineScope.launch {
                                            val personaActualizada: Persona = persona.copy(rol = nuevoRol.value)
                                            // Actualizar la persona en la lista
                                            personasList[index] = personaActualizada
                                            // Actualizar la persona en la base de datos
                                            val db = Firebase.firestore
                                            personaDao.updatePersona(personaActualizada)
                                            db.collection("Persona").document(personaActualizada.id.toString())
                                                .set(personaActualizada)
                                                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                                                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                                        }
                                    }
                                ) {
                                    Text("Confirmar")
                                }
                            },
                            dismissButton = {
                                Button(onClick = { mostrarDialog.value=false }) {
                                    Text(text = "Cancelar")
                                }
                            }

                        )
                    }
                }
            }
        }
    )
}