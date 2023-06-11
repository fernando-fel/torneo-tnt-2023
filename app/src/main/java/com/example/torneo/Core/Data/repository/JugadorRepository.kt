package com.example.torneo.Core.Data.repository
import com.example.torneo.Core.Data.Jugador
import com.example.torneo.Core.Data.Torneo
import kotlinx.coroutines.flow.Flow


typealias Jugadores = List<Jugador>
interface JugadorRepository {
    fun getJugadorFromRoom() : Flow<Jugadores>

    fun addJugadorToRoom(jugador: Jugador)

    /**
     * Delete item from the data source
     */
    suspend fun deleteJugador(jugador: Jugador)

    /**
     * Update item in the data source
     */
    suspend fun updateJugador(jugador: Jugador)

    fun getJugador(id: Int): Jugador

}