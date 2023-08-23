package com.example.torneo.Pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import Component.CustomTopAppBar
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.torneo.Core.Data.Dao.PersonaDao
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.R
import kotlinx.coroutines.launch
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


//import Component.CustomTopAppBar

@Composable
@ExperimentalMaterial3Api
fun SignUp(
    navController: NavHostController,
    persona: PersonaDao
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBar(navController, persona)
    }
}

@Composable
@ExperimentalMaterial3Api
fun ScaffoldWithTopBar(navController: NavHostController, persona: PersonaDao)
{
    // Inicializar el siguiente idPersona a partir de 3
    val idPersona = remember { mutableStateOf(0) }

    val nombre = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    //Ojo de ocultar o dar visibilidad los caracteres
    val passwordVisible = remember { mutableStateOf(false) }
    val passwordVisibilityIcon = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

    val registroOk = remember { mutableStateOf(false) }

    val nombreErrorText = if (nombre.value.isBlank()) "*Obligatorio" else ""
    val userErrorText = if (username.value.isBlank()) "*Obligatorio" else ""
    val passErrorText = if (password.value.isBlank()) "*Obligatorio" else ""

    val mostrarDialog = remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            CustomTopAppBar(navController, "Registrarse", true)
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp, 40.dp, 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image (
                    painter = painterResource(id = R.drawable.logo_v4),
                    contentDescription ="Logo",
                    modifier = Modifier.size(200.dp,200.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))

                //NOMBRE
                Column{
                    TextField(
                        label = { Text(text = "Nombre") },
                        value = nombre.value,
                        onValueChange = { nombre.value = it },
                        modifier = Modifier
                            .fillMaxWidth().padding(20.dp,5.dp),
                        singleLine = true,
                    )
                    Text(
                        text = nombreErrorText,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }

                //USUARIO
                Column {
                    TextField(
                        label = { Text(text = "Usuario") },
                        value = username.value,
                        onValueChange = { username.value = it },
                        modifier = Modifier
                            .fillMaxWidth().padding(20.dp,20.dp,20.dp,5.dp),
                        singleLine = true,
                    )
                    Text(
                        text = userErrorText,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }

                //CONTRASEÑA
                Column{
                    TextField(
                        label = { Text(text = "Contraseña") },
                        value = password.value,
                        onValueChange = { password.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp,20.dp,20.dp,5.dp),
                        singleLine = true,
                        visualTransformation =  if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(
                                onClick = { passwordVisible.value = !passwordVisible.value },
                            ) {
                                Icon(
                                    imageVector = passwordVisibilityIcon,
                                    contentDescription = if (passwordVisible.value) "Ocultar contraseña" else "Mostrar contraseña"
                                )
                            }
                        }
                    )
                    Text(
                        text = passErrorText,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(100.dp))

                val coroutineScope = rememberCoroutineScope()
                //Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)
                Box(modifier = Modifier.fillMaxSize().padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val usuario = Persona(id = idPersona.value, idPersona="1", nombre=nombre.value,
                                    username = username.value, pass = password.value, rol = "usuario")
                                val db = Firebase.firestore

                                persona.insertPersona(usuario)
                                db.collection("Persona").document(usuario.id.toString())
                                    .set(usuario)
                                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                                //personaId.value++

                                if (nombre.value.isNotBlank() && username.value.isNotBlank() &&
                                    password.value.isNotBlank()) {
                                    mostrarDialog.value = true
                                }

                                //navController.navigate(Routes.Login.route)
                            }
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !(nombre.value.isBlank() || username.value.isBlank() || password.value.isBlank())
                    ) {
                        Text(text = "Registrar")
                    }
                    if (mostrarDialog.value) {
                        AlertDialog(
                            onDismissRequest = { mostrarDialog.value = false },
                            title = { Text(text = "Exito") },
                            text = { Text(text = "El registro fue un Éxito!") },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        mostrarDialog.value = false
                                        nombre.value = ""
                                        username.value = ""
                                        password.value = ""
                                    }
                                    ) { Text(text = "Aceptar") }
                            },
                            backgroundColor = Color(0xFF1089CA),
                            contentColor = Color.White
                        )
                    }
                }
            }
        }
    )
}
