package tat.mukhutdinov.juiceTracker.ui.bottomsheet

import android.widget.RatingBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import tat.mukhutdinov.juiceTracker.R

@Composable
fun RatingInputRow(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    InputRow(
        inputLabel = stringResource(R.string.rating),
        modifier = modifier
    ) {
        AndroidView(
            factory = { context ->
                RatingBar(context).apply {
                    stepSize = 1f
                }
            },
            update = { ratingBar ->
                ratingBar.rating = rating.toFloat()
                ratingBar.setOnRatingBarChangeListener { _, newRating, _ ->
                    onRatingChange(newRating.toInt())
                }
            }
        )
    }
}