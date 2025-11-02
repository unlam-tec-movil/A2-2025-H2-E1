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
import ar.edu.unlam.mobile.scaffolding.ui.screens.FeedTuitsScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.FormScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.HomeScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.LogInScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.PostScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.UserScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
) {
    NavHost(navController = navController, startDestination = "logInScreen") {
//    NavHost(navController = navController, startDestination = "feedTuitScreen") {
//        NavHost(navController = controller, startDestination = HOME_SCREEN_ROUTE) {
        // composable es el componente que se usa para definir un destino de navegación.
        // Por parámetro recibe la ruta que se utilizará para navegar a dicho destino.
        composable("home") {
            // Home es el componente en sí que es el destino de navegación.
            HomeScreen(modifier = Modifier.padding(paddingValues = paddingValues))
        }

        composable("feedTuitScreen") {
            // Home es el componente en sí que es el destino de navegación.
            FeedTuitsScreen()
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
            )
        }
        composable("postTuiter") {
            PostScreen()
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
