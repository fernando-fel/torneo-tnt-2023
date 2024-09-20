package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.Dao.FechaDao
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Torneo
import kotlinx.coroutines.flow.Flow


class FechaRepositoryImpl(private val fechaDao: FechaDao
): FechaRepository{
    override fun getAllFechas() = fechaDao.getFechas()

    override suspend fun getFecha(id: Int) = fechaDao.getFechaById(id)

    override suspend fun addFecha(fecha: Fecha) = fechaDao.insertFecha(fecha)

    override fun getFechasByTorneo(id: Int) = fechaDao.getFechasByTorneoId(id)

    override fun getFechasFromRoom(): Flow<List<Fecha>> {
        TODO("Not yet implemented")
    }

    override fun getFechasByTorneoIdFromRoom(idTorneo: Int): Flow<List<Fecha>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFechaById(idFecha: Int): Fecha {
        TODO("Not yet implemented")
    }

    override fun addFechaToRoom(fecha: Fecha) {
        TODO("Not yet implemented")
    }


    override suspend fun deleteFecha(fecha: Fecha) = fechaDao.deleteFecha(fecha)

    override suspend fun updateFecha(fecha: Fecha) = fechaDao.updateFecha(fecha)
    override fun syncFirebaseToRoom() {
        TODO("Not yet implemented")
    }

}
