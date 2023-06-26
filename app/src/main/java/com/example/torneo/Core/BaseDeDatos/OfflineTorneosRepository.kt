package com.example.torneo.Core.BaseDeDatos


import com.example.torneo.Core.Data.Dao.TorneoDao
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Core.Data.repository.TorneoRepository
import com.example.torneo.Core.Data.repository.Torneos
import kotlinx.coroutines.flow.Flow

class OfflineTorneosRepository(private val torneoDao: TorneoDao) : TorneoRepository {
    override fun getAllTorneos() = torneoDao.getTorneos()
    override fun getTorneo(id: Int) = torneoDao.getTorneo(id)
    override suspend fun addTorneo(torneo: Torneo) = torneoDao.insertTorneo(torneo)

    override suspend fun deleteTorneo(torneo:Torneo) = torneoDao.deleteTorneo(torneo)

    override suspend fun updateTorneo(torneo: Torneo) = torneoDao.update(torneo)


}