package tat.mukhutdinov.juiceTracker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import tat.mukhutdinov.juiceTracker.R
import tat.mukhutdinov.juiceTracker.ui.bottomsheet.EntryBottomSheet
import tat.mukhutdinov.juiceTracker.ui.homescreen.AdBanner
import tat.mukhutdinov.juiceTracker.ui.homescreen.JuiceTrackerFAB
import tat.mukhutdinov.juiceTracker.ui.homescreen.JuiceTrackerList
import tat.mukhutdinov.juiceTracker.ui.homescreen.JuiceTrackerTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuiceTrackerApp(
    modifier: Modifier = Modifier,
    juiceTrackerViewModel: JuiceTrackerViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )

    val scope = rememberCoroutineScope()
    val trackerState by juiceTrackerViewModel.juiceListStream.collectAsState(emptyList())

    EntryBottomSheet(
        juiceTrackerViewModel = juiceTrackerViewModel,
        sheetScaffoldState = bottomSheetScaffoldState,
        modifier = modifier,
        onCancel = {
            scope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        },
        onSubmit = {
            juiceTrackerViewModel.saveJuice()
            scope.launch {
                bottomSheetScaffoldState.bottomSheetState.hide()
            }
        }
    )
    {
        Scaffold(
            topBar = {
                JuiceTrackerTopAppBar()
            },
            floatingActionButton = {
                JuiceTrackerFAB(
                    onClick = {
                        juiceTrackerViewModel.resetCurrentJuice()
                        scope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                    }
                )
            }
        ) { contentPadding ->
            Column(Modifier.padding(contentPadding)) {
                AdBanner(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_small)
                        )
                )

                JuiceTrackerList(
                    juices = trackerState,
                    onDelete = { juice -> juiceTrackerViewModel.deleteJuice(juice) },
                    onUpdate = { juice ->
                        juiceTrackerViewModel.updateCurrentJuice(juice)
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    },
                )
            }
        }
    }
}
