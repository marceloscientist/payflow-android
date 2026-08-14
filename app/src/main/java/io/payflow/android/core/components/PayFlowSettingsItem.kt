package io.payflow.android.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun PayFlowSettingsItem(
    title: String,
    onClick: () -> Unit,
    trailingIcon: ImageVector? = null,
    trailingIconTint: Color = LocalContentColor.current,
    trailingIconDescription: String? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            modifier = Modifier.weight(1f)
        )

        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = trailingIconDescription,
                tint = trailingIconTint
            )
        }
    }

    HorizontalDivider()
}