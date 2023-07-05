package com.example.torneo.Core.Data.repository

import com.example.torneo.Core.Data.Dao.JugadorDao
import com.example.torneo.Core.Data.Dao.PersonaDao
import com.example.torneo.Core.Data.Entity.Persona

import com.example.torneo.Core.Data.Jugador


class PersonaRepositoryImpl(private val personaDao: PersonaDao
): PersonaRepository{
    override fun getPersonaFromRoom()= personaDao.getAlphabetizedPersona()

    override fun addPersonaToRoom(persona: Persona) = personaDao.insertPersona(persona)

    override suspend fun deletePersona(persona: Persona) = personaDao.deletePersona(persona)

    override suspend fun updatePersona(persona: Persona) = personaDao.updatePersona(persona)

    override fun getPersona(id:Int) = personaDao.getPersona(id)

    //override fun getJugadoresPorEquipo (id: Int) = jugadorDao.getJugadoresByEquipo(id)

}