package com.example.torneo.Core

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log

import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController

import androidx.room.Room
import com.example.torneo.Core.BaseDeDatos.TorneoDB

import com.example.torneo.Core.Constantes.Companion.TORNEO_TABLE
import com.example.torneo.Pantallas.Routes
import com.example.torneo.Pantallas.ScreenMain

import com.example.torneo.ui.theme.TorneoTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.google.firebase.ktx.initialize

import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch

import sincronizar_db

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val MY_CHANNEL_ID = "my_channel"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val torneoId = intent.getIntExtra("TORNEO_ID", -1) // Obtén el torneoId
        Log.d(TAG, "torneoId: $torneoId")
        //val torneoId = 2

        // Inicializar Firebase
        Firebase.initialize(this)

        val database = Room.databaseBuilder(this, TorneoDB::class.java, TORNEO_TABLE)
            .fallbackToDestructiveMigration()
            .build()

        val db = Firebase.firestore
/*
        // Iniciar sincronización
        lifecycleScope.launch {
            try {
                sincronizar_db(db, database)
            } catch (e: Exception) {
                Log.e(TAG, "Error sincronizando base de datos", e)
            }
        }

 */



        setContent {
            TorneoTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val  startDestination = if (intent.getBooleanExtra("fromNotification", false)) {
                        "${Routes.FechaUsuarioScreen.route}/$torneoId"
                        }else{
                            Routes.SplashScreen.route
                        }
                    Log.d("Entreeeee",startDestination )
                        //ScreenMain(database,torneoId)

                            // Lógica para navegar a la pantalla correspondiente
                        if (torneoId != -1) {
                            val navController = rememberNavController()
                            // Navegar a la pantalla que necesites, por ejemplo:

                        }
                    }
                    ScreenMain(database = database,torneoId)
                }
            }
        }
    }


/*    private var canAuthenticate = false
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private fun setupAuth() {

        if (BiometricManager.from(this).canAuthenticate(
                BIOMETRIC_STRONG
                        or DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            canAuthenticate = true

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticacion Biometrica")
                .setSubtitle("autenticate usando el sensor")
                .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL).build()
        }
    }

    private fun authenticate(auth: (auth: Boolean) -> Unit) {
        if (canAuthenticate) {
            BiometricPrompt(this, ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        auth(true)
                    }
                }).authenticate(promptInfo)
        } else {
            auth(true)
        }
    }

    @Composable
    fun Auth() {
        var auth by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(if (auth)(Color.Blue) else Color.Green)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Text(if (auth) "Estas Dentro" else ("Login"),
                fontSize = 22.sp, fontWeight = FontWeight.Light)
            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                if (auth) {
                    auth = false
                } else {
                    authenticate {
                        auth = it
                    }
                }
            }) {
                Text(if (auth) "Cerrar" else "Login")

            }

        }
    }
}*/


/*

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TorneoTheme {
        Greeting("Android")
    }
}*/
