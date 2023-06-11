import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Geofence transition received")
        // Acciones a realizar cuando se recibe una transición del geofence
    }

    companion object {
        private const val TAG = "GeofenceBroadcastReceiver"
    }
}