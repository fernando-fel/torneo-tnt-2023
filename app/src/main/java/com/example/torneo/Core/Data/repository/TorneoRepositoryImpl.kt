package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.TorneoDao
import com.example.torneo.Core.Data.Torneo

class TorneoRepositoryImpl(
    private val torneoDao: TorneoDao
    ): TorneoRepository{
    override fun getAllTorneos() = torneoDao.getAlphabetizedTorneos()
    override suspend fun addTorneo(torneo: Torneo) = torneoDao.insert(torneo)
    override fun getTorneo(id: Int) = torneoDao.getTorneo(id)
    override suspend fun deleteTorneo(torneo: Torneo) = torneoDao.deleteTorneo(torneo)
    override suspend fun updateTorneo(torneo: Torneo) = torneoDao.update(torneo)
}