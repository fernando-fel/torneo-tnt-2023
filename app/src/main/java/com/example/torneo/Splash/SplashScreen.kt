package com.example.torneo.Splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.torneo.Pantallas.Routes
import com.example.torneo.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.popBackStack()
        navController.navigate(Routes.Login.route)
    }
    Splash()
}
@Composable
fun Splash(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image (
            modifier = Modifier.size(500.dp,300.dp),
            painter = painterResource(id = R.drawable.torneo2023),
            contentDescription ="Logo"
        )
        Text(
            text = "TNT 2023",
            modifier = Modifier.padding(vertical = 70.dp),
            style = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Serif
            )
        )
        //Spacer(modifier = Modifier.height(20.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){
    Splash()
}

