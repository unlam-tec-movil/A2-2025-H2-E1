package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ar.edu.unlam.mobile.scaffolding.utils.Constants.FORM_ROUTE

@Composable
fun BottomBar(controller: NavHostController) {
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "feedTuitScreen" } == true,
            onClick = { controller.navigate("feedTuitScreen") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "feedTuitScreen",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
        )
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "user/{id}" } == true,
            onClick = { controller.navigate("user/usuario") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "UserApiResponse",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
        )
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == FORM_ROUTE } == true,
            onClick = { controller.navigate(FORM_ROUTE) },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "UserApiResponse",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
        )
    }
}
