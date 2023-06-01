package com.example.torneo.Core.BaseDeDatos

import com.example.torneo.Core.Data.Torneo
import com.example.torneo.Core.Data.TorneoDao
import com.example.torneo.Core.Data.repository.TorneoRepository
import com.example.torneo.Core.Data.repository.Torneos
import kotlinx.coroutines.flow.Flow

class OfflineTorneosRepository(private val torneoDao: TorneoDao) : TorneoRepository {
    override fun getAllTorneos(): Flow<Torneos> = torneoDao.getAlphabetizedTorneos()
    override fun getTorneo(id: Int) = torneoDao.getTorneo(id)

    override suspend fun addTorneo(torneo: Torneo) = torneoDao.insert(torneo)

    override suspend fun deleteTorneo(torneo: Torneo) = torneoDao.deleteTorneo(torneo)

    override suspend fun updateTorneo(torneo: Torneo)= torneoDao.update(torneo)
}