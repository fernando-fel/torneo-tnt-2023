package com.example.torneo.Splash

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun
        MenuPrincipal(){
    Text("Hola")
/*    //val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        //topBar = { TopBar(scope,snackbarHostState},
        bottomBar =  {Drawer()}
    ){

    }*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scope : CoroutineScope,
    //snackbarState: { SnackbarHost(snackbarState)},
){}
    /*
    TopAppBar(
        title= { Text(text = "Torneo de futbol")},
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { snackbarState.showSnackbar("Drawer()}") }
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Icono de Menu")
            }
        }
        )

     */


@Composable
fun Drawer(){
    val menu_items = listOf(
        "Equipo",
        "Tablas",
        "Torneo",

    )
    Column() {
        menu_items.forEach{item ->
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = item,
                modifier = Modifier. fillMaxWidth())

            }
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun prueba() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Centered TopAppBar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val list = (0..5).map { it.toString() }
                items(count = list.size) {
                    Text(
                        text = list[it],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    )
}
@Preview(showBackground = true)
@Composable
fun ppreview(){
    prueba()
 //   MenuPrincipal()
        
    }
