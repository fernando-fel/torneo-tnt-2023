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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.R
import com.example.torneo.TorneoViewModel.PersonasViewModel

//import Component.CustomTopAppBar

@Composable
@ExperimentalMaterial3Api
fun SignUp(
    viewModel: PersonasViewModel = hiltViewModel(),
    navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBar(viewModel, navController)
    }
}

@Composable
@ExperimentalMaterial3Api
fun ScaffoldWithTopBar(
    viewModel: PersonasViewModel = hiltViewModel(),
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(navController, "Registrarse", true)
        },
        content = { padding ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFFD0BCFF)
            ) {
                Column(
                    //modifier = Modifier.padding(40.dp),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image (
                        modifier = Modifier.size(150.dp),
                        painter = painterResource(id = R.drawable.logo_4),
                        contentDescription ="Logo"
                    )
                    //Text(text = "Iniciar Sesion", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive))
                    Spacer(modifier = Modifier.height(30.dp))
                    var nombre = remember { mutableStateOf(TextFieldValue()) }
                    val username = remember { mutableStateOf(TextFieldValue()) }
                    val password = remember { mutableStateOf(TextFieldValue()) }
                    val inicioSesionOk = remember { mutableStateOf(false) }
                    val showErrorDialog = remember { mutableStateOf(false) }

                    //NOMBRE
                    TextField(
                        label = { Text(text = "Nombre y Apellido") },
                        value = nombre.value,
                        onValueChange = { username.value = it }
                    )

                    //USUARIO
                    TextField(
                        label = { Text(text = "Usuario") },
                        value = username.value,
                        onValueChange = { username.value = it }
                    )

                    //Ojo de ocultar o dar visibilidad los caracteres
                    val passwordVisible = remember { mutableStateOf(false) }
                    val passwordVisibilityIcon = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                    Spacer(modifier = Modifier.height(20.dp))

                    //CONTRASEÑA
                    TextField(
                        label = { Text(text = "Contraseña") },
                        value = password.value,
                        //visualTransformation = PasswordVisualTransformation(),
                        visualTransformation =  if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        onValueChange = { password.value = it },
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

                    Spacer(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                val persona = Persona(0, idPersona = 0, nombre=nombre.toString(),
                                    username = username.toString(), pass = password.toString(), rol = "usuario")
                                viewModel.addPersona(persona)
                                navController.navigate(Routes.Login.route)
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Registrarse")
                        }
                    }
                }
            }
        }
    )
}
