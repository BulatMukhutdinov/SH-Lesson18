package tat.mukhutdinov.juiceTracker.data

import android.content.Context

/**
 * [tat.mukhutdinov.juiceTracker.data.AppContainer] implementation that provides instance of [RoomJuiceRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [JuiceRepository]
     */
    override val juiceRepository: JuiceRepository by lazy {
        RoomJuiceRepository(AppDatabase.getDatabase(context).juiceDao())
    }
}
