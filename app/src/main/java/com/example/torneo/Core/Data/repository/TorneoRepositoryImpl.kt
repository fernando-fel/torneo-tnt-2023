package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.Dao.TorneoDao
import com.example.torneo.Core.Data.Entity.Equipo

import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Core.Data.Entity.TorneoEquipo
import kotlinx.coroutines.runBlocking

class TorneoRepositoryImpl(private val torneoDao: TorneoDao
): TorneoRepository{
    override fun getAllTorneos() = torneoDao.getTorneos()

    override suspend fun getTorneo(id: Int)= torneoDao.getTorneo(id.toString())

    override suspend fun addTorneo(torneo: Torneo) = torneoDao.insertTorneo(torneo)

    override suspend fun deleteTorneo(torneo: Torneo)= torneoDao.deleteTorneo(torneo)

    override suspend fun updateTorneo(torneo: Torneo) = torneoDao.update(torneo)

    override suspend fun getEquiposEnTorneo(torneoId: String) = torneoDao.getEquiposEnTorneo(torneoId)

    override suspend fun inscribirEquipos(torneoId: Int, equipos: List<Equipo>) {
        val torneoEquipos = equipos.map { equipo ->
            TorneoEquipo(torneoId = torneoId, equipoId = equipo.id)
        }
        torneoDao.inscribirEquipos(torneoEquipos)
    }
    override fun getCountTorneos(): Int {
        return runBlocking {
            torneoDao.getCantidadTorneos()
        }
    }



}