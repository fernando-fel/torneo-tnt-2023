package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.Dao.PartidoDao

import com.example.torneo.Core.Data.Entity.Partido



class PartidoRepositoryImpl(
    private val partidoDao: PartidoDao
): PartidoRepository{
    override fun getAllPartidos() = partidoDao.getPartidos()

    override fun getPartido(id: Int) = partidoDao.getPartidoById(id)

    override suspend fun addPartido(partido: Partido) = partidoDao.insertPartido(partido)

    override suspend fun deletePartido(partido: Partido) = partidoDao.deletePartido(partido)

    override suspend fun updatePartido(partido: Partido) = partidoDao.updatePartido(partido)

}