package com.example.torneo.Core.Data.repository


import com.example.torneo.Core.Data.Dao.PersonaDao
import com.example.torneo.Core.Data.Entity.Persona
import kotlinx.coroutines.runBlocking

class PersonaRepositoryImpl(private val personaDao: PersonaDao
): PersonaRepository{
    override fun getAllPersonas()= personaDao.getAlphabetizedPersona()

    override suspend fun addPersona(persona: Persona) = personaDao.addPersona(persona)

    override suspend fun deletePersona(persona: Persona) = personaDao.deletePersona(persona)

    override suspend fun updatePersona(persona: Persona) = personaDao.updatePersona(persona)

    override suspend fun getPersona(id:Int) = personaDao.getPersona(id)

    override suspend fun getPersonaList(): List<Persona> {
        return personaDao.getPersonaList()
    }

    override fun getCountPersonas(): Int {
        return runBlocking {
            personaDao.getCantidadPersonas()
        }
    }

}