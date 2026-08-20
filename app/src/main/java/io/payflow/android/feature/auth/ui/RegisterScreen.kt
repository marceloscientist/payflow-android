package io.payflow.android.feature.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import io.payflow.android.core.components.PayFlowButton
import io.payflow.android.core.components.PayFlowCard
import io.payflow.android.core.components.PayFlowTextField
import io.payflow.android.core.components.model.PayFlowButtonType
import io.payflow.android.core.theme.PayFlowSpacing
import isValidEmail

@Composable
fun RegisterScreen(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    isLoading: Boolean,
    errorMessage: String?,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onBackToLoginClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(PayFlowSpacing.LG),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Criar conta",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(PayFlowSpacing.SM))

        Text(
            text = "Cadastre-se para começar a gerenciar suas assinaturas",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.padding(PayFlowSpacing.XL))

        PayFlowCard(modifier = Modifier.fillMaxWidth()) {

            PayFlowTextField(
                value = name,
                onValueChange = onNameChange,
                label = "Nome"
            )

            Spacer(modifier = Modifier.padding(PayFlowSpacing.MD))

            PayFlowTextField(
                value = email,
                onValueChange = onEmailChange,
                label = "E-mail"
            )

            Spacer(modifier = Modifier.padding(PayFlowSpacing.MD))

            PayFlowTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = "Senha",
                isPassword = true
            )

            Spacer(modifier = Modifier.padding(PayFlowSpacing.MD))

            PayFlowTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = "Confirmar senha",
                isPassword = true
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.padding(PayFlowSpacing.SM))
                Text(
                    text = errorMessage,
                    color = Color(0xFFD32F2F),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(PayFlowSpacing.LG))

            PayFlowButton(
                text = if (isLoading) "Cadastrando..." else "Cadastrar",
                type = PayFlowButtonType.PRIMARY,
                enabled = !isLoading
                        && name.isNotBlank()
                        && email.isValidEmail()
                        && password.length >= 6
                        && confirmPassword.isNotBlank(),
                onClick = onRegisterClick
            )

            Spacer(modifier = Modifier.padding(PayFlowSpacing.SM))

            PayFlowButton(
                text = "Já tenho conta - Fazer login",
                type = PayFlowButtonType.SECONDARY,
                enabled = !isLoading,
                onClick = onBackToLoginClick
            )
        }
    }
}

