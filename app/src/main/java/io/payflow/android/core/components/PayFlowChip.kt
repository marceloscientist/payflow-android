package io.payflow.android.core.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PayFlowChip(
    label: String,
    onClick: () -> Unit
) {

    AssistChip(
        onClick = onClick,
        label = {
            Text(label)
        }
    )
}