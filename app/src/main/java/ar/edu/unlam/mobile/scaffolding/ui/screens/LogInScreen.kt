package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.SnackbarVisualsWithError
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.LogInViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.LoginState
import ar.edu.unlam.mobile.scaffolding.utils.validateForm
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    navController: NavController,
    logInViewModel: LogInViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    modifier: Modifier,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // estado del login del viewModel
    val logInRequestStatus by logInViewModel.loginState.collectAsState()

    // estados de UI
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rememberSession by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    // auto-login si guardó sesión
    val rememberedUser by logInViewModel.userDataStore.rememberedUser.collectAsState(initial = null)

    LaunchedEffect(rememberedUser) {
        delay(100)
        if (!rememberedUser.isNullOrBlank()) {
            navController.navigate("feedTuitScreen") {
                popUpTo("loginScreen") { inclusive = true }
            }
        }
    }

    LaunchedEffect(logInRequestStatus) {
        when (logInRequestStatus) {
            is LoginState.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        visuals =
                            SnackbarVisualsWithError(
                                message = (logInRequestStatus as LoginState.Error).message,
                                isError = true,
                            ),
                    )
                }
                logInViewModel.resetState()
            }

            is LoginState.Success -> {
                Toast
                    .makeText(
                        context,
                        (logInRequestStatus as LoginState.Success).message,
                        Toast.LENGTH_SHORT,
                    ).show()
                logInViewModel.resetState()
                navController.navigate("feedTuitScreen") {
                    popUpTo(route = "loginScreen") { inclusive = true }
                }
            }

            LoginState.Idle, LoginState.Loading -> Unit
        }
    }
    Column(
        modifier = modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "HELLO!\n Welcome to TUITER",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier.height(16.dp),
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Ocultar" else "Mostrar",
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = rememberSession,
                onCheckedChange = { rememberSession = it },
            )
            Text(text = "Remember me")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    val res = validateForm(email, password)
                    if (!res.isValid) {
                        snackbarHostState.showSnackbar(
                            visuals = SnackbarVisualsWithError(res.message, true),
                        )
                    } else {
                        logInViewModel.logInVM(
                            email = email,
                            password = password,
                            remember = rememberSession,
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "LOG IN")
        }

        Spacer(modifier = Modifier.weight(1f))

        // Registro
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "¿Don´t have an account?")
            // Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = { navController.navigate("registerScreen") }) {
                Text("Sign Up")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
