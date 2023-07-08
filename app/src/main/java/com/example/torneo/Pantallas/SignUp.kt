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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import Component.CustomTopAppBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.torneo.Core.Constantes
import com.example.torneo.Core.Data.Dao.PersonaDao
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.R
import com.example.torneo.TorneoViewModel.PersonasViewModel
import com.google.android.gms.maps.model.CircleOptions
import kotlinx.coroutines.launch
import java.text.Normalizer

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
fun ScaffoldWithTopBar(
    navController: NavHostController,
    persona: PersonaDao
) {

    val mostrarDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomTopAppBar(navController, "Registrarse", true)
        },
        content = { padding ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                //color = Color(0xFFEAE7F0)
            ) {
                Column(
                    //modifier = Modifier.padding(40.dp),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image (
                        modifier = Modifier.size(150.dp).clip(CircleShape),
                        painter = painterResource(id = R.drawable.logo_3),
                        contentDescription ="Logo"
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    // Inicializar el siguiente idPersona a partir de 3
                    val personaId = remember { mutableStateOf(3) }
                    var nombre = remember { mutableStateOf("") }
                    val username = remember { mutableStateOf("") }
                    val password = remember { mutableStateOf("") }
                    val registroOk = remember { mutableStateOf(false) }

                    //NOMBRE
                    OutlinedTextField(
                        label = { Text(text = "Nombre") },
                        value = nombre.value,
                        onValueChange = { nombre.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    //USUARIO
                    OutlinedTextField(
                        label = { Text(text = "Usuario") },
                        value = username.value,
                        onValueChange = { username.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    //Ojo de ocultar o dar visibilidad los caracteres
                    val passwordVisible = remember { mutableStateOf(false) }
                    val passwordVisibilityIcon = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                    //CONTRASEÑA
                    OutlinedTextField(
                        label = { Text(text = "Contraseña") },
                        value = password.value,
                        onValueChange = { password.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
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

                    Spacer(modifier = Modifier.height(40.dp))

                    val coroutineScope = rememberCoroutineScope()
                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    val usuario = Persona(idPersona = personaId.value, nombre=nombre.value,
                                        username = username.value, pass = password.value, rol = "usuario")
                                    persona.insertPersona(usuario)
                                    personaId.value++
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
                                .height(50.dp)
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
                                        onClick = { mostrarDialog.value = false }
                                        ) { Text(text = "Aceptar") }
                                },
                                backgroundColor = Color(0xFF1089CA),
                                contentColor = Color.White
                            )
                        }
                    }
                }
            }
        }
    )
}
