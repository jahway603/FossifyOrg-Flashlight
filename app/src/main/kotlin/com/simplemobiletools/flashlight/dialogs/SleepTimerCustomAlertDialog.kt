package com.simplemobiletools.flashlight.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.simplemobiletools.commons.R
import com.simplemobiletools.commons.compose.alert_dialog.AlertDialogState
import com.simplemobiletools.commons.compose.alert_dialog.rememberAlertDialogState
import com.simplemobiletools.commons.compose.extensions.MyDevices
import com.simplemobiletools.commons.compose.theme.AppThemeSurface
import com.simplemobiletools.commons.dialogs.DialogSurface

private val items = listOf(
    R.string.minutes_raw,
    R.string.seconds_raw,
)

@Composable
fun SleepTimerCustomAlertDialog(
    alertDialogState: AlertDialogState,
    modifier: Modifier = Modifier,
    onConfirmClick: (seconds: Int) -> Unit,
    onCancelClick: () -> Unit = {}
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    var value by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = alertDialogState::hide
    ) {
        DialogSurface(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(all = dimensionResource(id = R.dimen.big_margin))
            ) {
                Text(
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.activity_margin)),
                    text = stringResource(id = R.string.sleep_timer),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Column(
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.activity_margin),
                        end = dimensionResource(id = R.dimen.activity_margin),
                        top = dimensionResource(id = R.dimen.activity_margin),
                    )
                ) {
                    TextField(
                        modifier = Modifier.padding(
                            bottom = dimensionResource(id = R.dimen.normal_margin),
                        ),
                        value = value,
                        onValueChange = {
                            value = it.filter { it.isDigit() }
                        },
                        label = {
                            Text(stringResource(id = R.string.value))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    items.forEachIndexed { index, item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = index == selectedItem,
                                onClick = {
                                    selectedItem = index
                                }
                            )
                            Text(
                                text = stringResource(id = item)
                            )
                        }
                    }
                }

                Row(
                    Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onCancelClick()
                        alertDialogState.hide()
                    }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    TextButton(onClick = {
                        val enteredValue = Integer.valueOf(value.ifEmpty { "0" })
                        val multiplier = getMultiplier(items[selectedItem])
                        onConfirmClick(enteredValue * multiplier)
                        alertDialogState.hide()
                    }) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            }
        }
    }
}

private fun getMultiplier(id: Int) = when (id) {
    R.string.seconds_raw -> 1
    R.string.minutes_raw -> 60
    else -> 60
}

@Composable
@MyDevices
private fun SleepTimerCustomAlertDialogPreview() {
    AppThemeSurface {
        SleepTimerCustomAlertDialog(
            alertDialogState = rememberAlertDialogState(),
            onConfirmClick = {},
            onCancelClick = {},
        )
    }
}
