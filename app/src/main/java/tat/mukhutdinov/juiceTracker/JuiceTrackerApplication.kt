package tat.mukhutdinov.juiceTracker

import android.app.Application
import tat.mukhutdinov.juiceTracker.data.AppContainer
import tat.mukhutdinov.juiceTracker.data.AppDataContainer

class JuiceTrackerApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
