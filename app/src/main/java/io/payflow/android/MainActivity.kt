package io.payflow.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.payflow.android.core.navigation.PayFlowNavGraph
import io.payflow.android.core.theme.PayFlowTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PayFlowTheme {
                PayFlowNavGraph()
            }
        }
    }
}