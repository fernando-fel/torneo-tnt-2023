package com.example.torneo.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.torneo.R
import com.example.torneo.Splash.Splash


@Composable
fun LoginPage(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().padding(40.dp,0.dp,40.dp,30.dp), contentAlignment = Alignment.BottomCenter) {
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
        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val inicioSesionOk = remember { mutableStateOf(false) }
        val showErrorDialog = remember { mutableStateOf(false) }

        TextField(
            label = { Text(text = "Nombre de Usuario") },
            value = username.value,
            onValueChange = { username.value = it })

        //Ojo de ocultar o dar visibilidad los caracteres
        val passwordVisible = remember { mutableStateOf(false) }
        val passwordVisibilityIcon = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff


        Spacer(modifier = Modifier.height(20.dp))
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
                    val nombre = username.value.text
                    val contrasenia = password.value.text

                    if (nombre == "admin" && contrasenia == "admin") {
                        inicioSesionOk.value = true
                        navController.navigate(Routes.SesionOk.route)
                        // Acción para iniciar sesión correctamente
                        // Pantalla Home, Alta torneo, Ver Partidos, Ver Fechas
                    } else if (nombre == "usuario" && contrasenia == "usuario"){
                        inicioSesionOk.value = true
                        navController.navigate(Routes.Fixture.route)
                    } else if (nombre == "juez" && contrasenia == "juez"){
                        inicioSesionOk.value = true
                        navController.navigate(Routes.UnPartido.route)
                    } else {
                        showErrorDialog.value = true
                        // Acción para mostrar un mensaje de error o realizar otra acción
                        //navController.navigate(Routes.SesionIncorrecto.route)
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Iniciar Sesion")
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
        ClickableText(
            text = AnnotatedString("¿Has olvidado tu contraseña?"),
            onClick = { navController.navigate(Routes.ForgotPassword.route) },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary
                //fontFamily = FontFamily.Default
            )
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun Preview(){
    LoginPage()
}*/

