package com.example.torneo.Core.Di
import com.example.torneo.Core.BaseDeDatos.TorneoDB
import com.example.torneo.Core.Data.repository.TorneoRepository
import android.content.Context
import com.example.torneo.Core.BaseDeDatos.OfflineTorneosRepository
import com.example.torneo.Core.BaseDeDatos.TorneoDB_Impl

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val torneoRepository: TorneoRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */

/*class AppDataContainer(private val context: Context) : AppContainer {
    override val torneoRepository: TorneoRepository by lazy {
        OfflineTorneosRepository(TorneoDB.getDatabase(context).torneoDao())
    }
}*/
