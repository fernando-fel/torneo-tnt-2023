package com.example.torneo.Core

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.torneo.Core.BaseDeDatos.TorneoDB
import com.example.torneo.Core.BaseDeDatos.sincronizar_db
import com.example.torneo.Core.Constantes.Companion.TORNEO_TABLE
import com.example.torneo.Core.Data.Dao.PersonaDao
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.Pantallas.ScreenMain

import com.example.torneo.ui.theme.TorneoTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.ktx.initialize

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object{
        const val MY_CHANNEL_ID = "my_channel"
    }
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database =
            Room.databaseBuilder(this, TorneoDB::class.java, TORNEO_TABLE)
                .fallbackToDestructiveMigration().build()
        val db = Firebase.firestore
        // Add a new document with a generated ID
        //sincronizar_db(db,database)
        setContent {
            TorneoTheme (
                darkTheme = false
            ){
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    /*val fb_db = Firebase.firestore
                    sincronizar_db(fb_db, database)

                    Firebase.initialize(context = this)
                    Firebase.appCheck.installAppCheckProviderFactory(
                        PlayIntegrityAppCheckProviderFactory.getInstance(),
                    )*/
                    //Firebase.c
                    //Firebase.appCheck.installAppCheckProviderFactory(
                    //  PlayIntegrityAppCheckProviderFactory.getInstance(),
                    //)
                    //myMarket()

                    ScreenMain(database = database)
                    //Notificaciones()
                    //AppNavigation()
                }
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
