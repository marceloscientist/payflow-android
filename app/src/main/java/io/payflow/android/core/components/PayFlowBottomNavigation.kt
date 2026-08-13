package io.payflow.android.core.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import io.payflow.android.core.config.AppConfig
import io.payflow.android.core.navigation.Routes

@Composable
fun PayFlowBottomNavigation(
    navController: NavHostController,
    currentRoute: String?
) {

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == Routes.Dashboard.route,
            onClick = {
                navController.navigate(Routes.Dashboard.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Dashboard"
                )
            },
            label = {
                Text("Dashboard")
            }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Subscriptions.route,
            onClick = {
                navController.navigate(Routes.Subscriptions.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Assinaturas"
                )
            },
            label = {
                Text("Assinaturas")
            }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Simulator.route,
            onClick = {
                navController.navigate(Routes.Simulator.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.AttachMoney,
                    contentDescription = "Simulador"
                )
            },
            label = {
                Text("Simulador")
            }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.Profile.route,
            onClick = {
                navController.navigate(Routes.Profile.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil"
                )
            },
            label = {
                Text("Perfil")
            }
        )

        if (AppConfig.SHOW_DEVELOPER_PLAYGROUND) {
            NavigationBarItem(
                selected = currentRoute == Routes.DeveloperPlayground.route,
                onClick = {
                    navController.navigate(Routes.DeveloperPlayground.route)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = "Developer Playground"
                    )
                },
                label = {
                    Text("Mais")
                }
            )
        }
    }
}