package io.payflow.android.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PayFlowSubscriptionCard(
    serviceName: String,
    logoUrl: String? = null,
    plan: String,
    price: String,
    billingInfo: String,
    modifier: Modifier = Modifier
) {

    PayFlowCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PayFlowServiceLogo(
                serviceName = serviceName,
                logoUrl = logoUrl,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = serviceName,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = plan,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = price,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = billingInfo,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}