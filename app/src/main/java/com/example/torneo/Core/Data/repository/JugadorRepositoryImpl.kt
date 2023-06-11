package com.example.torneo.Core.Data.repository
import com.example.torneo.Core.Data.Dao.EquipoDao
import com.example.torneo.Core.Data.Dao.JugadorDao
import com.example.torneo.Core.Data.Equipo
import com.example.torneo.Core.Data.Jugador
import com.example.torneo.Core.Data.TorneoDao
import kotlinx.coroutines.flow.Flow

class JugadorRepositoryImpl(private val jugadorDao: JugadorDao
): JugadorRepository{
    override fun getJugadorFromRoom()= jugadorDao.getAlphabetizedJugador()

    override fun addJugadorToRoom(jugador: Jugador) = jugadorDao.insertJugador(jugador)

    override suspend fun deleteJugador(jugador: Jugador) = jugadorDao.deleteJugador(jugador)

    override suspend fun updateJugador(jugador: Jugador) = jugadorDao.updateJugador(jugador)

    override fun getJugador(id:Int) = jugadorDao.getJugador(id)


}