package tat.mukhutdinov.juiceTracker.data

import android.content.Context

/**
 * [AppContainer] implementation that provides instance of [RoomJuiceRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [JuiceRepository]
     */
    override val trackerRepository: JuiceRepository by lazy {
        RoomJuiceRepository(AppDatabase.getDatabase(context).juiceDao())
    }
}
