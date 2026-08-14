package io.payflow.android.feature.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import io.payflow.android.core.components.PayFlowButton
import io.payflow.android.core.components.PayFlowCard
import io.payflow.android.core.components.PayFlowTextField
import io.payflow.android.core.components.model.PayFlowButtonType
import io.payflow.android.core.theme.PayFlowSpacing
import isValidEmail

@Composable
fun LoginScreen(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PayFlowSpacing.LG),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "PayFlow",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.padding(
                PayFlowSpacing.SM
            )
        )

        Text(
            text = "Gerencie suas assinaturas em um único lugar",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.padding(
                PayFlowSpacing.XL
            )
        )

        PayFlowCard(
            modifier = Modifier.fillMaxWidth()
        ) {

            PayFlowTextField(
                value = email,
                onValueChange = onEmailChange,
                label = "E-mail"
            )

            Spacer(
                modifier = Modifier.padding(
                    PayFlowSpacing.MD
                )
            )

            PayFlowTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = "Senha",
                isPassword = true
            )

            Spacer(
                modifier = Modifier.padding(
                    PayFlowSpacing.LG
                )
            )

            PayFlowButton(
                text = "Entrar",
                type = PayFlowButtonType.PRIMARY,
                enabled = email.isValidEmail()
                        && password.isNotBlank(),
                onClick = onLoginClick
            )
        }
    }
}
