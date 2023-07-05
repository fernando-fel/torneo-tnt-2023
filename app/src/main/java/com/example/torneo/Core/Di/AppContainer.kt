package com.example.torneo.Core.Di
import com.example.torneo.Core.Data.repository.TorneoRepository
import com.example.torneo.Core.Data.repository.EquipoRepository
import com.example.torneo.Core.Data.repository.FechaRepository
import com.example.torneo.Core.Data.repository.PersonaRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val torneoRepository: TorneoRepository
    val equipoRepository: EquipoRepository
    val personaRepository: PersonaRepository
    val fechaRepository: FechaRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */

/*class AppDataContainer(private val context: Context) : AppContainer {
    override val torneoRepository: TorneoRepository by lazy {
        OfflineTorneosRepository(TorneoDB.getDatabase(context).torneoDao())
    }
}*/
