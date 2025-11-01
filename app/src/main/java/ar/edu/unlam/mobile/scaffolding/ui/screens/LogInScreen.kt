package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import ar.edu.unlam.mobile.scaffolding.ui.components.SnackbarVisualsWithError
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.LogInViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.LoginState
import ar.edu.unlam.mobile.scaffolding.utils.validateForm
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    navController: NavController,
    logInViewModel: LogInViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    modifier: Modifier,
) {
    val toastContex = LocalContext.current
    val scope = rememberCoroutineScope()
    val logInRequestStatus by logInViewModel.loginState.collectAsState()

    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()

    LaunchedEffect(key1 = logInRequestStatus) {
        when (logInRequestStatus) {
            is LoginState.Error -> {
                snackbarHostState.showSnackbar(
                    visuals =
                        SnackbarVisualsWithError(
                            message = (logInRequestStatus as LoginState.Error).message,
                            isError = true,
                        ),
                )
                emailState.clearText()
                passwordState.clearText()
                logInViewModel.resetState()
            }

            LoginState.Idle -> {} // es el estado antes de la request, asi que no necesita codigo
            LoginState.Loading -> {}
            is LoginState.Success -> {
                Toast
                    .makeText(
                        toastContex,
                        (logInRequestStatus as LoginState.Success).message,
                        Toast.LENGTH_SHORT,
                    ).show()
                emailState.clearText()
                passwordState.clearText()
                logInViewModel.resetState()
                navController.navigate("feedTuitScreen") {
                    popUpTo(route = "loginScreen") {
                        inclusive = true
                    }
                }
            }
        }
    }
    Column(
        modifier =
            modifier
                .padding(16.dp)
                .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            state = emailState,
            supportingText = { Text(text = "email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        TextField(
            state = passwordState,
            supportingText = { Text(text = "password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        Button(
            onClick = {
                val res = validateForm(passwordState.text.toString(), emailState.text.toString())
                scope.launch {
                    if (!res.isValid) {
                        snackbarHostState.showSnackbar(
                            visuals =
                                SnackbarVisualsWithError(
                                    res.message,
                                    !res.isValid,
                                ),
                        )
                        emailState.clearText()
                        passwordState.clearText()
                    }
                    if (res.isValid) {
                        logInViewModel.logInVM(
                            email = emailState.text.toString(),
                            password = passwordState.text.toString(),
                        )
                    }
                }
            },
        ) { Text(text = "iniciar sesion") }
    }
}
