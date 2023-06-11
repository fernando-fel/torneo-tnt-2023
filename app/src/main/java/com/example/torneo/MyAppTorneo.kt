package com.example.torneo

import GeofenceBroadcastReceiver
import android.Manifest
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyAppTorneo : Application(){
      override fun onCreate() {
            super.onCreate()
            setupGeofencing()
        }

        private fun setupGeofencing() {
            val geofencingClient = LocationServices.getGeofencingClient(this)

            // Configura tus geovallas aquí
            val geofenceList = mutableListOf<Geofence>()

            // Agrega las geovallas al cliente de geovallas
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
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
            geofencingClient.addGeofences(getGeofencingRequest(geofenceList), getGeofencePendingIntent())
                .addOnSuccessListener {
                    // Maneja el éxito de agregar las geovallas
                }
                .addOnFailureListener {
                    // Maneja el error de agregar las geovallas
                }
        }

        private fun getGeofencingRequest(geofences: List<Geofence>): GeofencingRequest {
            return GeofencingRequest.Builder()
                .addGeofences(geofences)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build()
        }

        private fun getGeofencePendingIntent(): PendingIntent {
            val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
            return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }


