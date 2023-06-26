package com.example.torneo.Core.Di

import android.content.Context
import androidx.room.Room
import com.example.torneo.Core.BaseDeDatos.TorneoDB
import com.example.torneo.Core.Constantes.Companion.TORNEO_TABLE
import com.example.torneo.Core.Data.Dao.EquipoDao
import com.example.torneo.Core.Data.Dao.FechaDao
import com.example.torneo.Core.Data.Dao.JugadorDao
import com.example.torneo.Core.Data.Dao.PartidoDao
import com.example.torneo.Core.Data.Dao.TorneoDao

import com.example.torneo.Core.Data.repository.EquipoRepository
import com.example.torneo.Core.Data.repository.EquipoRepositoryImpl
import com.example.torneo.Core.Data.repository.FechaRepository
import com.example.torneo.Core.Data.repository.FechaRepositoryImpl

import com.example.torneo.Core.Data.repository.JugadorRepository
import com.example.torneo.Core.Data.repository.JugadorRepositoryImpl
import com.example.torneo.Core.Data.repository.PartidoRepository
import com.example.torneo.Core.Data.repository.PartidoRepositoryImpl
import com.example.torneo.Core.Data.repository.TorneoRepository
import com.example.torneo.Core.Data.repository.TorneoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@JvmSuppressWildcards
class AppModule {

    private var Instance: TorneoDB? = null
    @Provides
    @JvmSuppressWildcards
    fun provideTorneoDb(
        @ApplicationContext
        context : Context) = Room.databaseBuilder(context, TorneoDB::class.java,TORNEO_TABLE).fallbackToDestructiveMigration().build()
        .also{Instance = it}

    @Singleton
    @Provides
    @JvmSuppressWildcards
    fun provideTorneoDao(
        torneoDB: TorneoDB) = torneoDB.torneoDao()

    @Singleton
    @Provides
    @JvmSuppressWildcards
    fun provideTorneoRepository(
        torneoDao: TorneoDao
    ): TorneoRepository {
        return TorneoRepositoryImpl(
        torneoDao = torneoDao)}


    @Singleton
    @Provides
    @JvmSuppressWildcards
    fun provideEquipoDao(
        torneoDB: TorneoDB) = torneoDB.equipoDao()

    @Singleton
    @Provides
    @JvmSuppressWildcards
    fun provideEquipoRepository(
        equipoDao: EquipoDao
    ): EquipoRepository {
        return EquipoRepositoryImpl(
            equipoDao = equipoDao)
    }

    @Singleton
    @Provides
    @JvmSuppressWildcards
    fun provideFechaDao(
        torneoDB: TorneoDB) = torneoDB.fechaDao()

    @Singleton
    @Provides
    @JvmSuppressWildcards
    fun provideFechaRepository(
        fechaDao: FechaDao
    ): FechaRepository {
        return FechaRepositoryImpl(
            fechaDao = fechaDao)
    }



    @Singleton
    @Provides
    @JvmSuppressWildcards
    fun providePartidoDao(
        torneoDB: TorneoDB) = torneoDB.partidoDao()

    @Singleton
    @Provides
    @JvmSuppressWildcards
    fun providePartidoRepository(
        partidoDao: PartidoDao
    ): PartidoRepository {
        return PartidoRepositoryImpl(
            partidoDao = partidoDao)
    }
}