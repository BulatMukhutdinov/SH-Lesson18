package tat.mukhutdinov.juiceTracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import tat.mukhutdinov.juiceTracker.data.Juice
import tat.mukhutdinov.juiceTracker.data.JuiceRepository

/**
 * ViewModel to retrieve, delete a juice entry from the [JuiceRepository]'s data source.
 */
class TrackerViewModel(private val juiceRepository: JuiceRepository) : ViewModel() {

    val juicesStream: Flow<List<Juice>> = juiceRepository.juicesStream

    fun deleteJuice(juice: Juice) = viewModelScope.launch {
        juiceRepository.deleteJuice(juice)
    }
}
