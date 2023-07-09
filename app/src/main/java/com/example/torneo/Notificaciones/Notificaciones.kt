@file:Suppress("UNUSED_EXPRESSION")

package com.example.torneo.Notificaciones

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.torneo.R


@Composable
fun Notificaciones(){
    val context = LocalContext.current
    val idChanell = "idFechas"
    val idNotificacion = 0
    val textoLargo =
        "Estoy creando una notificacion de texto largo"+
                "Debe aparecer u icono a la derecha." +
                "Esto es para ver como expande"
    val iconoGrande = BitmapFactory.decodeResource(context.resources, R.drawable.logo_v1)

    LaunchedEffect(Unit){
        crearCanalNotificacion(idChanell,context)
    }
    val modifier = Modifier
        .padding(18.dp)
        .fillMaxWidth()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
         ) {
        Text(
            text = "Notificacion",
            style = MaterialTheme.typography.h3
        )
        Button(
            onClick = {
                notificacionBasica(
                    context,
                    idChanell,
                    idNotificacion,
                    "Notificacion Simple",
                    "Soy una Notificacion"
                )
            },
            modifier = modifier
        ) {
            Text(text = "Notificacion Simple")

        }
        Button(
            onClick = {
                      notificacionTextoLargo(
                          context,
                          idChanell,
                          idNotificacion +1,
                          "Notificacion con texto largo",
                          textoLargo,
                          iconoGrande
                      )
                      },
            modifier = modifier
        ) {
            Text(text = "Notificacion con texto Largo")

        }

        Button(
            onClick = { /*TO DO*/ },
            modifier = modifier
        ) {
            Text(text = "Notificacion Programada")

        }
    }
}

fun notificacionTextoLargo(context: Context,
                           idChanell: String,
                           idNotificacion: Int,
                           textTitle: String,
                           textoLargo: String,
                           iconoGrande: Bitmap?,
                            priority: Int = NotificationCompat.PRIORITY_DEFAULT) {
    var builder = NotificationCompat.Builder(context,idChanell)
        .setSmallIcon(R.drawable.logo_v1)
        .setContentTitle(textTitle)
        .setContentText(textoLargo)
        .setLargeIcon(iconoGrande)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(textoLargo)
        )
        .setPriority(priority)
    with(NotificationManagerCompat.from(context)){
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notify(idNotificacion, builder.build())
    }
    }

fun notificacionBasica(context: Context, idChanell: String, idNotificacion: Int, textTitle: String, textContent: String,
priority: Int = NotificationCompat.PRIORITY_DEFAULT) {
    val builder = NotificationCompat.Builder(context,idChanell)
        .setSmallIcon(R.drawable.logo_v2)
        .setContentTitle(textTitle)
        .setContentTitle(textContent)
        .setPriority(priority)
    with(NotificationManagerCompat.from(context)){

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notify(idNotificacion, builder.build())
    }
}

fun crearCanalNotificacion(
        idChannel: String,
        context: Context
    ){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val nombre = "TorneoNotification"
            val descripcion = "Canal de notificacion para fechas"
            val importancia = NotificationManager.IMPORTANCE_DEFAULT
            val canal = NotificationChannel(idChannel,nombre,importancia)
                .apply {
                    descripcion
                }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as
                        NotificationManager
            notificationManager.createNotificationChannel(canal)

        }
    }