package com.example.torneo.Core.BaseDeDatos


import com.example.torneo.Core.Data.Dao.TorneoDao
import com.example.torneo.Core.Data.Dao.TorneoEquipoDao
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Core.Data.Entity.TorneoEquipo
import com.example.torneo.Core.Data.repository.TorneoRepository
import com.example.torneo.Core.Data.repository.Torneos
import kotlinx.coroutines.flow.Flow

class OfflineTorneosRepository(private val torneoDao: TorneoDao,private val torneoEquipoDao: TorneoEquipoDao) : TorneoRepository {
    override fun getAllTorneos() = torneoDao.getTorneos()
    override suspend fun getTorneo(id: Int): Torneo = torneoDao.getTorneo(id)
    override suspend fun addTorneo(torneo: Torneo) = torneoDao.insertTorneo(torneo)

    override suspend fun deleteTorneo(torneo:Torneo) = torneoDao.deleteTorneo(torneo)

    override suspend fun updateTorneo(torneo: Torneo) = torneoDao.update(torneo)
    override suspend fun inscribirEquipos(torneoId: Int, equipos: List<Equipo>) {
        val torneoEquipos = equipos.map { equipo ->
            TorneoEquipo(torneoId = torneoId, equipoId = equipo.id)
        }
        torneoDao.inscribirEquipos(torneoEquipos)
    }
    override suspend fun getEquiposEnTorneo(torneoId: Int) = torneoDao.getEquiposEnTorneo(torneoId)
    override fun getCountTorneos(): Int {
        TODO("Not yet implemented")
    }

}