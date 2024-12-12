package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.TorneoViewModel.PersonasViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalMaterial3Api
@Composable
fun ListadoDePersonas(
    navController: NavHostController,
    personaDao: PersonasViewModel
) {
    val mostrarDialog = remember { mutableStateOf(false) }
    val personasList = remember { mutableStateListOf<Persona>() }
    val searchQuery = remember { mutableStateOf("") }
    val selectedPersona = remember { mutableStateOf<Persona?>(null) }
    val scope = rememberCoroutineScope()

    // Lista de roles disponibles
    val roles = listOf("admin", "usuario", "juez")

    // Cargar datos
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val personas = personaDao.getPersonaList()
            personasList.addAll(personas)
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(navController, "Gestión de Usuarios", true)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar usuario...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                singleLine = true
            )

            // Lista de usuarios
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                personasList
                    .filter {
                        it.nombre.contains(searchQuery.value, ignoreCase = true) ||
                                it.username.contains(searchQuery.value, ignoreCase = true)
                    }
                    .forEach { persona ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            elevation = CardDefaults.elevatedCardElevation(),
                            onClick = {
                                selectedPersona.value = persona
                                mostrarDialog.value = true
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Información del usuario
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = "Usuario",
                                        modifier = Modifier.size(40.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Column {
                                        Text(
                                            text = persona.nombre,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "@${persona.username}",
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Surface(
                                            shape = MaterialTheme.shapes.small,
                                            color = MaterialTheme.colorScheme.secondaryContainer
                                        ) {
                                            Text(
                                                text = "Rol: " + persona.rol,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        }
                                    }
                                }

                                // Botón de edición
                                IconButton(onClick = {
                                    selectedPersona.value = persona
                                    mostrarDialog.value = true
                                }) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Editar",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
            }
        }

        // Diálogo de edición
        if (mostrarDialog.value && selectedPersona.value != null) {
            var expanded by remember { mutableStateOf(false) }
            var selectedRole by remember { mutableStateOf(selectedPersona.value?.rol ?: roles[0]) }

            AlertDialog(
                onDismissRequest = {
                    mostrarDialog.value = false
                    selectedPersona.value = null
                },
                title = {
                    Text(
                        "Modificar Rol de Usuario",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                text = {
                    Column {
                        Text(
                            "Usuario: ${selectedPersona.value?.nombre}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // ExposedDropdownMenuBox para selección de rol
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selectedRole,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                label = { Text("Seleccionar Rol") }
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                roles.forEach { rol ->
                                    DropdownMenuItem(
                                        text = { Text(rol) },
                                        onClick = {
                                            selectedRole = rol
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            scope.launch {
                                selectedPersona.value?.let { persona ->
                                    val personaActualizada = persona.copy(rol = selectedRole)
                                    val index = personasList.indexOf(persona)
                                    if (index != -1) {
                                        personasList[index] = personaActualizada
                                    }

                                    // Actualizar en Firebase y base de datos local
                                    val db = Firebase.firestore
                                    personaDao.updatePersona(personaActualizada)
                                    db.collection("Persona")
                                        .document(personaActualizada.id.toString())
                                        .set(personaActualizada)
                                        .addOnSuccessListener {
                                            Log.d(TAG, "Usuario actualizado correctamente")
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w(TAG, "Error al actualizar usuario", e)
                                        }
                                }
                                mostrarDialog.value = false
                                selectedPersona.value = null
                            }
                        }
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            mostrarDialog.value = false
                            selectedPersona.value = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}