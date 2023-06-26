package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.Dao.EquipoDao
import com.example.torneo.Core.Data.Entity.Equipo

import kotlinx.coroutines.flow.Flow

class EquipoRepositoryImpl(private val equipoDao: EquipoDao
): EquipoRepository{
    override fun getEquipoFromRoom() =  equipoDao.getAlphabetizedEquipo()

    override fun addEquipoToRoom(equipo: Equipo) = equipoDao.insertEquipo(equipo)

    override suspend fun deleteEquipo(equipo: Equipo) = equipoDao.deleteEquipo(equipo)


    override suspend fun updateEquipo(equipo: Equipo) = equipoDao.updateEquipo(equipo)

    override suspend fun getEquipo(id: Int) = equipoDao.getEquipo(id)

}