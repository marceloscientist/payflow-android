package io.payflow.android.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.payflow.android.core.components.model.PayFlowStatusType

@Composable
fun PayFlowStatusBadge(
    type: PayFlowStatusType
) {

    val backgroundColor = when (type) {

        PayFlowStatusType.ACTIVE ->
            Color(0xFFDFF5E1)

        PayFlowStatusType.CANCELLED ->
            Color(0xFFFFE0E0)

        PayFlowStatusType.PENDING ->
            Color(0xFFFFF3CD)

        PayFlowStatusType.EXPIRED ->
            Color(0xFFE5E5E5)
    }

    val textColor = when (type) {

        PayFlowStatusType.ACTIVE ->
            Color(0xFF2E7D32)

        PayFlowStatusType.CANCELLED ->
            Color(0xFFC62828)

        PayFlowStatusType.PENDING ->
            Color(0xFFEF6C00)

        PayFlowStatusType.EXPIRED ->
            Color(0xFF616161)
    }

    Text(
        text = type.name,
        color = textColor,
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
    )
}