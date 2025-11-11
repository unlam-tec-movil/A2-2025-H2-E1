package ar.edu.unlam.mobile.scaffolding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.navigation.AppNavigation
import ar.edu.unlam.mobile.scaffolding.ui.components.BottomBar
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomSnackBar
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldingV2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    // Controller es el elemento que nos permite navegar entre pantallas. Tiene las acciones
    // para navegar como naviegate y también la información de en dónde se "encuentra" el usuario
    // a través del back stack
    val controller = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }

    // Solo muestra el boton de postear tuit en la pantalla de feed.
    // creamos un estado que nos dice cual es la ruta actual en tiempo real
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "form" && currentRoute != "logInScreen") {
                BottomBar(controller = controller)
            }
        },
        floatingActionButton = {
            if (currentRoute == "feedTuitScreen") {
                androidx.compose.material3.FloatingActionButton(
                    onClick = { controller.navigate("postTuiter") },
                ) {
                    Icon(Icons.Default.Create, contentDescription = "Home")
                }
            }

        },
        snackbarHost = {
            CustomSnackBar(snackBarHostState)
        },
    ) { paddingValue ->
        AppNavigation(
            navController = controller,
            paddingValues = paddingValue,
            snackbarHostState = snackBarHostState,
        )
    }
}
