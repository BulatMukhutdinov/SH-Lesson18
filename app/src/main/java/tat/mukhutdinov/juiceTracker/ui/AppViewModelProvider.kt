package tat.mukhutdinov.juiceTracker.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import tat.mukhutdinov.juiceTracker.JuiceTrackerApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEntryViewModel
        initializer {
            EntryViewModel(juiceTrackerApplication().container.trackerRepository)
        }
        // Initializer for TrackerViewModel
        initializer {
            TrackerViewModel(juiceTrackerApplication().container.trackerRepository)
        }
    }
}

/**
 * Extension function to query for [Application] object and returns an instance of
 * [JuiceTrackerApplication].
 */
fun CreationExtras.juiceTrackerApplication(): JuiceTrackerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as JuiceTrackerApplication)
