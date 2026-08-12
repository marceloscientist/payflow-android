package io.payflow.android.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PayFlowSubscriptionCard(
    serviceName: String,
    plan: String,
    price: String,
    billingInfo: String,
    modifier: Modifier = Modifier
) {

    PayFlowCard(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = serviceName,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = plan,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = price,
            style = MaterialTheme.typography.titleSmall
        )

        Text(
            text = billingInfo,
            style = MaterialTheme.typography.bodySmall
        )
    }
}