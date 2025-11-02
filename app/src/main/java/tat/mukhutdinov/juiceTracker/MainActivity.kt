package tat.mukhutdinov.juiceTracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import tat.mukhutdinov.juiceTracker.ui.JuiceTrackerApp
import tat.mukhutdinov.juiceTracker.ui.theme.JuiceTrackerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JuiceTrackerTheme {
                JuiceTrackerApp()
            }
        }
    }
}
