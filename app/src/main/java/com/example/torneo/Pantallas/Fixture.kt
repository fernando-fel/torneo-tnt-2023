package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Fixture(navController: NavHostController){
    val fixtureData = FixtureData(
        numFechas = 1,
        partidos = listOf(
            Partido("Equipo A", "Equipo B", 0,9),
            Partido("Equipo C", "Equipo D", 0, 0),
            Partido("Equipo E", "Equipo F", 0, 0),
            Partido("Equipo G", "Equipo H", 0, 0),
            Partido("Equipo I", "Equipo J", 0, 0)
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldFixture(navController, fixtureData)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ScaffoldFixture(navController: NavHostController, fixtureData: FixtureData) {
    Scaffold (
        topBar = {
            CustomTopAppBar(navController,"Fixture", true)
        },
        content = {
            FixtureView(navController,fixtureData)
        }
    )
}

@Composable
fun FixtureView(navController: NavHostController, fixtureData: FixtureData) {
    val gradient = Brush.radialGradient(
        0.5f to Color.Transparent,
        1.0f to Color.Blue,
        radius = 1500.0f,
        tileMode = TileMode.Repeated
    )

    Column(
        modifier = Modifier
            .background(gradient)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Fixture", fontSize = 40.sp)
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0C7C11))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            Text(
                text = "Fecha: ${fixtureData.numFechas}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0C7C11))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            Text(
                text = "Sábado 3 de Junio del 2023",
                fontWeight = FontWeight.Normal,
                color = Color.White,
                fontSize = 15.sp
            )
        }

        fixtureData.partidos.forEach { partido ->
            PartidoItem(navController, partido)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0C7C11))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            Text(
                text = "Fecha: 2",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0C7C11))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            Text(
                text = "Sabado 10 de Junio del 2023",
                fontWeight = FontWeight.Normal,
                color = Color.White,
                fontSize = 15.sp
            )
        }
        fixtureData.partidos.forEach { partido ->
            PartidoItem(navController, partido)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0C7C11))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            Text(
                text = "Fecha: 3",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0C7C11))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            Text(
                text = "Domingo 18 de Junio del 2023",
                fontWeight = FontWeight.Normal,
                color = Color.White,
                fontSize = 15.sp
            )
        }
        fixtureData.partidos.forEach { partido ->
            PartidoItem(navController, partido)
        }
    }
}

@Composable
fun PartidoItem(navController: NavHostController,partido: Partido) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    navController.navigate("${Routes.UnPartido.route}/${partido.equipoLocal}/${partido.equipoVisitante}/${partido.golLocal}/${partido.golVisitante}")
                    //navController.navigate("${Routes.UnPartido.route}/${partido}")
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                text = "${partido.equipoLocal}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(text = "${partido.golLocal}", fontSize = 20.sp)
            Text(text = "${partido.golVisitante}", fontSize = 20.sp)
            Text(
                text = "${partido.equipoVisitante}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

// Clases de datos

data class FixtureData(
    val numFechas: Int,
    val partidos: List<Partido>
)

data class Partido(
    val equipoLocal: String,
    val equipoVisitante: String,
    var golLocal: Int,
    val golVisitante:Int,
)

/*
@Preview
@Composable
fun PreviewFixture() {
    Fixture()
}*/