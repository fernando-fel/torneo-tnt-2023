package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.Dao.EquipoDao
import com.example.torneo.Core.Data.Entity.Equipo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class EquipoRepositoryImpl(
    private val equipoDao: EquipoDao
) : EquipoRepository {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val equipoRef: DatabaseReference = database.getReference("equipos")

    override fun getEquipoFromRoom(): Flow<List<Equipo>> = equipoDao.getAlphabetizedEquipo()

    override fun addEquipoToRoom(equipo: Equipo) {
        // Add or update in Room
        equipoDao.insertEquipo(equipo)
        // Add or update in Firebase
        equipoRef.child(equipo.id.toString()).setValue(equipo)
    }

    override suspend fun deleteEquipo(equipo: Equipo) {
        // Delete from Room
        equipoDao.deleteEquipo(equipo)
        // Delete from Firebase
        equipoRef.child(equipo.id.toString()).removeValue()
    }

    override suspend fun updateEquipo(equipo: Equipo) {
        // Update in Room
        equipoDao.updateEquipo(equipo)
        // Update in Firebase
        equipoRef.child(equipo.id.toString()).setValue(equipo)
    }

    override suspend fun getEquipo(id: Int): Equipo {
        // Try to get from Room
        val equipo = equipoDao.getEquipo(id)
        // Ensure the data is in Firebase
        equipoRef.child(id.toString()).get().addOnSuccessListener { snapshot ->
            val firebaseEquipo = snapshot.getValue(Equipo::class.java)
            if (firebaseEquipo != null && firebaseEquipo != equipo) {
                // Update Room if Firebase data is different
                runBlocking {
                    equipoDao.updateEquipo(firebaseEquipo)
                }
            }
        }.addOnFailureListener {
            // Handle possible errors
        }
        return equipo
    }

    // Sync Firebase data to Room
    fun syncFirebaseToRoom() {
        equipoRef.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                val equipos = snapshot.children.mapNotNull { it.getValue(Equipo::class.java) }
                runBlocking {
                    equipoDao.deleteAll()
                    equipoDao.insertAll(equipos)
                }
            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                // Handle possible errors
            }
        })
    }
}
