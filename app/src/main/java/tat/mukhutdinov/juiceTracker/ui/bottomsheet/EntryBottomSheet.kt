package tat.mukhutdinov.juiceTracker.ui.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import tat.mukhutdinov.juiceTracker.R
import tat.mukhutdinov.juiceTracker.data.Juice
import tat.mukhutdinov.juiceTracker.data.JuiceColor
import tat.mukhutdinov.juiceTracker.ui.JuiceTrackerViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBottomSheet(
    juiceTrackerViewModel: JuiceTrackerViewModel,
    sheetScaffoldState: BottomSheetScaffoldState,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val juice by juiceTrackerViewModel.currentJuiceStream.collectAsState()

    BottomSheetScaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars),
        scaffoldState = sheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Column {
                SheetHeader()
                SheetForm(
                    juice = juice,
                    onUpdateJuice = juiceTrackerViewModel::updateCurrentJuice,
                    onCancel = onCancel,
                    onSubmit = onSubmit
                )
            }
        }
    ) {
        content()
    }
}

@Composable
fun SheetHeader(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            modifier = modifier.padding(8.dp),
            text = stringResource(R.string.bottom_sheet_headline),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        HorizontalDivider()
    }
}

@Composable
fun SheetForm(
    juice: Juice,
    onUpdateJuice: (Juice) -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(horizontal = 16.dp)) {
        TextInputRow(
            inputLabel = stringResource(R.string.juice_name),
            fieldValue = juice.name,
            onValueChange = { name -> onUpdateJuice(juice.copy(name = name)) }
        )
        TextInputRow(
            inputLabel = stringResource(R.string.juice_description),
            fieldValue = juice.description,
            onValueChange = { description -> onUpdateJuice(juice.copy(description = description)) }
        )
        ColorSpinnerRow(
            colorSpinnerPosition = findColorIndex(juice.color),
            onColorChange = { color ->
                onUpdateJuice(juice.copy(color = JuiceColor.entries[color].name))
            }
        )
        RatingInputRow(
            rating = juice.rating,
            onRatingChange = { rating -> onUpdateJuice(juice.copy(rating = rating)) }
        )
        ButtonRow(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onCancel = onCancel,
            onSubmit = onSubmit,
            submitButtonEnabled = juice.name.isNotEmpty()
        )
    }
}

@Composable
fun ButtonRow(
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    submitButtonEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        OutlinedButton(
            onClick = onCancel,
            border = null
        ) {
            Text(stringResource(android.R.string.cancel).uppercase(Locale.getDefault()))
        }
        Button(
            onClick = onSubmit,
            enabled = submitButtonEnabled
        ) {
            Text(stringResource(R.string.save).uppercase(Locale.getDefault()))
        }
    }
}

@Composable
fun TextInputRow(
    inputLabel: String,
    fieldValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    InputRow(inputLabel, modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = fieldValue,
            onValueChange = onValueChange,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
    }
}

@Composable
fun InputRow(
    inputLabel: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier.padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = inputLabel,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .weight(1f)
                .padding(end = 8.dp),
        )
        Box(modifier = Modifier.weight(2f)) {
            content()
        }
    }
}

private fun findColorIndex(color: String): Int {
    val juiceColor = JuiceColor.valueOf(color)
    return JuiceColor.entries.indexOf(juiceColor)
}