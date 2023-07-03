package com.example.torneo.Mapas

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker


@Composable
fun myMarket(){
    //val marker = LatLng(-43.268135,-65.313812)
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
    GoogleMap(modifier = Modifier.fillMaxHeight(),
    properties = properties,
        uiSettings = uiSettings
    ){
        Marker(position = LatLng(-43.268135,-65.313812), title= "Independiente", snippet = "30% de descuento en medias para pies")
        Marker(LatLng(-43.257421,-65.306174), title= "Racing", snippet = "25% de descuento en cosas innecesarias")
    }
}

