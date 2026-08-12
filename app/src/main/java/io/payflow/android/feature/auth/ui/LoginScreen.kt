package io.payflow.android.feature.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.payflow.android.core.components.PayFlowButton
import io.payflow.android.core.components.model.PayFlowButtonType

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("PayFlow")

        PayFlowButton(
            text = "Entrar",
            type = PayFlowButtonType.PRIMARY,
            onClick = onLoginClick
        )
    }
}