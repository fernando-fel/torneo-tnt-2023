import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.torneo.Core.BaseDeDatos.TorneoDB
import com.example.torneo.Core.BaseDeDatos.sincronizar_db
import com.google.firebase.firestore.FirebaseFirestore

class SyncViewModel(private val db: FirebaseFirestore, private val localDb: TorneoDB) : ViewModel() {

    init {
        startListeningForUpdates()
    }

    private fun startListeningForUpdates() {
        db.collection("Torneo").addSnapshotListener { snapshot, e ->
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
        db.collection("Fecha").addSnapshotListener { snapshot, e ->
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
        db.collection("Partido").addSnapshotListener { snapshot, e ->
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
        db.collection("Equipo").addSnapshotListener { snapshot, e ->
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