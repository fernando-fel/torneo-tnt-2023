import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.torneo.Core.BaseDeDatos.TorneoDB
import com.google.firebase.firestore.FirebaseFirestore

class SyncViewModel(private val db: FirebaseFirestore, private val localDb: TorneoDB) : ViewModel() {

    init {
        startListeningForUpdates()
    }

    private fun startListeningForUpdates() {
        db.collection("torneos").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            snapshot?.let {
                if (!it.isEmpty) {
                    sincronizar_db(db, localDb)
                }
            }
        }
        db.collection("fechas").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            snapshot?.let {
                if (!it.isEmpty) {
                    sincronizar_db(db, localDb)
                }
            }
        }
        db.collection("partidos").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            snapshot?.let {
                if (!it.isEmpty) {
                    sincronizar_db(db, localDb)
                }
            }
        }
        db.collection("equipos").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            snapshot?.let {
                if (!it.isEmpty) {
                    sincronizar_db(db, localDb)
                }
            }
        }

    }
}