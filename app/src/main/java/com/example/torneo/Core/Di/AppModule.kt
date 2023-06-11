package com.example.torneo.Core.Di

import android.content.Context
import androidx.room.Room
import com.example.torneo.Core.BaseDeDatos.TorneoDB
import com.example.torneo.Core.Constantes.Companion.TORNEO_TABLE
import com.example.torneo.Core.Data.Dao.EquipoDao
import com.example.torneo.Core.Data.Dao.JugadorDao
import com.example.torneo.Core.Data.TorneoDao
import com.example.torneo.Core.Data.repository.EquipoRepository
import com.example.torneo.Core.Data.repository.EquipoRepositoryImpl
import com.example.torneo.Core.Data.repository.JugadorRepository
import com.example.torneo.Core.Data.repository.JugadorRepositoryImpl
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
    fun provideJugadorDao(
        torneoDB: TorneoDB) = torneoDB.jugadorDao()

    @Singleton
    @Provides
    @JvmSuppressWildcards
    fun provideJugadorRepository(
        jugadorDao: JugadorDao
    ): JugadorRepository {
        return JugadorRepositoryImpl(
            jugadorDao = jugadorDao)
    }

}