package tat.mukhutdinov.juiceTracker.data

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val trackerRepository: JuiceRepository
}
