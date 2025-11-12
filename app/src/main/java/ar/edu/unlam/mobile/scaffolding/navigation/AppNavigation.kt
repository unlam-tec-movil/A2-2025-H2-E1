package ar.edu.unlam.mobile.scaffolding.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ar.edu.unlam.mobile.scaffolding.ui.screens.EditUserScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.FeedTuitsScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.FormScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.HomeScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.LogInScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.PostScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.ReplyScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.TuitScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.UserScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
) {
    NavHost(
        navController = navController,
        startDestination = "logInScreen",
    ) {
        composable("home") {
            // Home es el componente en sí que es el destino de navegación.
            HomeScreen(modifier = Modifier.padding(paddingValues = paddingValues))
        }

        composable("feedTuitScreen") {
            // Home es el componente en sí que es el destino de navegación.
            FeedTuitsScreen(navController = navController)
        }
        composable("logInScreen") {
            // Home es el componente en sí que es el destino de navegación.
            LogInScreen(
                modifier = Modifier.padding(paddingValues = paddingValues),
                snackbarHostState = snackbarHostState,
                navController = navController,
            )
        }
        composable("form") {
            FormScreen(
                modifier = Modifier.padding(paddingValues),
                snackbarHostState = snackbarHostState,
                navController = navController,
            )
        }
        composable("editUserScreen") {
            EditUserScreen(
                paddingValues = paddingValues,
                navController = navController,
            )
        }
        composable("postTuiter") {
            PostScreen(
                navController = navController,
            )
        }

        composable(
            route = "replyScreen/{tuitId}",
            arguments = listOf(navArgument("tuitId") { type = NavType.StringType }),
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("tuitId") ?: "44"
            ReplyScreen(tuitId = id.toInt(), navController = navController)
        }

        composable(
            route = "tuitScreen/{tuitId}",
            arguments = listOf(navArgument("tuitId") { type = NavType.StringType }),
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("tuitId") ?: "44"
            TuitScreen(tuitId = id.toInt(), navController = navController)
        }

        composable(
            route = "user/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id") ?: "1"
            UserScreen(userId = id, modifier = Modifier.padding(paddingValues = paddingValues))
        }
    }
}
