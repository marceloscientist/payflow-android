package io.payflow.android.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PayFlowProfileHeader(
    name: String,
    email: String
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}