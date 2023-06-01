package com.example.torneo.Pantallas

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PrimeraPantalla(navController: NavController){
    Scaffold(){
        BodyContent(navController = navController)
    }
}

@Composable
fun BodyContent(navController: NavController) {
    //MediaList()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        Text(
            text = "Voy Pantalla",
            fontWeight = FontWeight.Bold
        )
        Button(onClick = {
            //navController.navigate(route = AppScreen.SecondScreen.route)
        }) {
            Text("Iniciar")
        }


    }
}
