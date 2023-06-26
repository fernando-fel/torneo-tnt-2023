package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.Dao.JugadorDao

import com.example.torneo.Core.Data.Jugador


class JugadorRepositoryImpl(private val jugadorDao: JugadorDao
): JugadorRepository{
    override fun getJugadorFromRoom()= jugadorDao.getAlphabetizedJugador()

    override fun addJugadorToRoom(jugador: Jugador) = jugadorDao.insertJugador(jugador)

    override suspend fun deleteJugador(jugador: Jugador) = jugadorDao.deleteJugador(jugador)

    override suspend fun updateJugador(jugador: Jugador) = jugadorDao.updateJugador(jugador)

    override fun getJugador(id:Int) = jugadorDao.getJugador(id)

    override fun getJugadoresPorEquipo (id: Int) = jugadorDao.getJugadoresByEquipo(id)

}