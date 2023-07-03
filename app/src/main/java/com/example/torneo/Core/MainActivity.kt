package com.example.torneo.Core

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.torneo.Core.MainActivity.Companion.MY_CHANNEL_ID
import com.example.torneo.Mapas.myMarket
import com.example.torneo.Notificaciones.Notificaciones
import com.example.torneo.Pantallas.ScreenMain


import com.example.torneo.ui.theme.TorneoTheme
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
class MainActivity : AppCompatActivity() {
    companion object{
        const val MY_CHANNEL_ID = "my_channel"
    }
    @OptIn(ExperimentalMaterial3Api::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorneoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Firebase.initialize(context = this)
                    Firebase.appCheck.installAppCheckProviderFactory(
                        PlayIntegrityAppCheckProviderFactory.getInstance(),
                    )
                    //myMarket()
                    ScreenMain()
                    //Notificaciones()
                    //AppNavigation()
                    //Esta es propia se ve
                    //NavGraph(navController = rememberNavController())
                    //Greeting("Android")
                }
            }
        }
        //setupAuth()

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
