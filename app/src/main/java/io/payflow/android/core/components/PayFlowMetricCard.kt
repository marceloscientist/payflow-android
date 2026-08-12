package io.payflow.android.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PayFlowMetricCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    PayFlowCard(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}