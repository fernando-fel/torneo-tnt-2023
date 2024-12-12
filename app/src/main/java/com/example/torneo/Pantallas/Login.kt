package com.example.torneo.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.material.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.IconButton

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.R
import com.example.torneo.TorneoViewModel.PersonasViewModel
import kotlinx.coroutines.launch


@Composable
fun Login(navController: NavHostController, persona: PersonasViewModel) {

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showErrorDialog = remember { mutableStateOf(false) }

    //ICONO OJO: oculta o da visibilidad DE los caracteres
    val passwordVisible = remember { mutableStateOf(false) }
    val passwordVisibilityIcon = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff


    var estadoInicioSesion by remember { mutableStateOf(false) }
    var personaRegistrada by remember { mutableStateOf<Persona?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var personasList by remember { mutableStateOf<List<Persona>>(emptyList()) }

    LaunchedEffect(key1 = Unit) {
        personasList = persona.getPersonaList()
    }
    Column(
        //modifier = Modifier.padding(40.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 60.dp, 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image (
            painter = painterResource(id = R.drawable.logo_v4),
            contentDescription ="Logo",
            modifier = Modifier.size(200.dp, 200.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            label = { Text(text = "Nombre de Usuario") },
            value = username.value,
            onValueChange = { username.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Contraseña") },
            value = password.value,
            onValueChange = { password.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
        Spacer(modifier = Modifier.height(30.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    // Llamar a getPersona dentro de un bloque de coroutine
                    coroutineScope.launch {
                        personaRegistrada = persona.login(username.value, password.value, personasList) //Llamar a la función de inicio de sesión en ViewModel
                        estadoInicioSesion = personaRegistrada != null
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Iniciar Sesion")
            }

            if (estadoInicioSesion) {
                var esRol = false
                when (personaRegistrada?.rol) {
                    "admin" -> {
                        navController.navigate(Routes.SesionOk.route)
                        esRol = true
                    }
                    "juez" -> {
                        navController.navigate("${Routes.PartidosDelJuezScreen.route}/${personaRegistrada?.id}")
                        esRol = true
                    }
                    "usuario" -> {
                        navController.navigate(Routes.MenuUsuario.route)
                        esRol = true
                    }
                }
                if (!esRol) {
                    showErrorDialog.value = true
                }
            }
            if (showErrorDialog.value) {
                AlertDialog(
                    onDismissRequest = { showErrorDialog.value = false },
                    title = { Text(text = "Error de inicio de sesión") },
                    text = { Text(text = "Clave o Usuario Incorrecto. Por favor, intenta nuevamente.") },
                    confirmButton = {
                        Button(
                            onClick = { showErrorDialog.value = false },
                        ) {
                            Text(text = "Aceptar")
                        }
                    },
                    backgroundColor = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        /*ClickableText(
            text = AnnotatedString("¿Has olvidado tu contraseña?"),
            onClick = { navController.navigate(Routes.ForgotPassword.route) },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary
            )
        )*/

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp, 0.dp, 40.dp, 30.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    navController.navigate(Routes.SignUp.route) },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Registrarse",
                )
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun Preview(){
    LoginPage()
}*/

