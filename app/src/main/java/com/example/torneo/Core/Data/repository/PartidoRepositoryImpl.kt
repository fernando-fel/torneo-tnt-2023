package com.example.torneo.Core.Data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

import com.example.torneo.Core.Data.Dao.PartidoDao

import com.example.torneo.Core.Data.Entity.Partido
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class PartidoRepositoryImpl(
    private val partidoDao: PartidoDao
): PartidoRepository{
    override fun getAllPartidos() = partidoDao.getPartidos()

    override fun getPartido(id: Int) = partidoDao.getPartidoById(id)

    override suspend fun addPartido(partido: Partido) = partidoDao.insertPartido(partido)

    override suspend fun deletePartido(partido: Partido) = partidoDao.deletePartido(partido)

    override suspend fun updatePartido(partido: Partido) = partidoDao.updatePartido(partido)

/*
Este andaaaa
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getPartidosDeHoy(): Flow<List<Partido>> {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") // Ajusta el formato según tu base de datos

        val date= LocalDate.now().format(formatter) // Asegúrate de tener la importación correspondiente
        Log.d("partiodo hooooy", date.toString())
        return partidoDao.getPartidosByDate(date.toString()) // Necesitarás implementar esto en tu DAO
    }
*/
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getPartidosDeHoyDetalle(): Flow<List<PartidoDao.PartidoConDetalles>> {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") // Ajusta el formato según tu base de datos

    val date = LocalDate.now().format(formatter) // Asegúrate de tener la importación correspondiente
    val partidoss = partidoDao.getPartidosByDate(date.toString())
    Log.d("PArtidosssss", partidoss.toString())
    return partidoss
    }
}