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
import androidx.compose.foundation.layout.*
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
fun Notificaciones() {
    val context = LocalContext.current
    val idChannel = "idFechas"
    val idNotificationBase = 0
    val textoLargo = "Estoy creando una notificación de texto largo. " +
            "Debe aparecer un icono a la derecha. " +
            "Esto es para ver cómo expande."
    val iconoGrande = BitmapFactory.decodeResource(context.resources, R.drawable.logo_v1)

    LaunchedEffect(Unit) {
        crearCanalNotificacion(idChannel, context)
    }

    val modifier = Modifier.padding(18.dp).fillMaxWidth()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Notificación",
            style = MaterialTheme.typography.h3
        )
        Button(
            onClick = {
                notificacionBasica(
                    context,
                    idChannel,
                    idNotificationBase,
                    "Notificación Simple",
                    "Soy una Notificación"
                )
            },
            modifier = modifier
        ) {
            Text(text = "Notificación Simple")
        }
        Button(
            onClick = {
                notificacionTextoLargo(
                    context,
                    idChannel,
                    idNotificationBase + 1,
                    "Notificación con texto largo",
                    textoLargo,
                    iconoGrande
                )
            },
            modifier = modifier
        ) {
            Text(text = "Notificación con texto Largo")
        }
        Button(
            onClick = { /* TO DO: Implementar notificación programada */ },
            modifier = modifier
        ) {
            Text(text = "Notificación Programada")
        }
    }
}

fun notificacionTextoLargo(context: Context,
                           idChannel: String,
                           idNotification: Int,
                           textTitle: String,
                           textoLargo: String,
                           iconoGrande: Bitmap?,
                           priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val builder = NotificationCompat.Builder(context, idChannel)
        .setSmallIcon(R.drawable.logo_v1)
        .setContentTitle(textTitle)
        .setContentText(textoLargo)
        .setLargeIcon(iconoGrande)
        .setStyle(NotificationCompat.BigTextStyle().bigText(textoLargo))
        .setPriority(priority)

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitar permiso aquí
            return
        }
        notify(idNotification, builder.build())
    }
}

fun notificacionBasica(context: Context, idChannel: String, idNotification: Int,
                       textTitle: String, textContent: String,
                       priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val builder = NotificationCompat.Builder(context, idChannel)
        .setSmallIcon(R.drawable.logo_v2)
        .setContentTitle(textTitle)
        .setContentText(textContent)  // Cambiado de setContentTitle a setContentText
        .setPriority(priority)

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitar permiso aquí
            return
        }
        notify(idNotification, builder.build())
    }
}

fun crearCanalNotificacion(idChannel: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val nombre = "TorneoNotification"
        val descripcion = "Canal de notificación para fechas"
        val importancia = NotificationManager.IMPORTANCE_DEFAULT
        val canal = NotificationChannel(idChannel, nombre, importancia).apply {
            this.description = descripcion // Corregido para establecer la descripción
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(canal)
    }
}
