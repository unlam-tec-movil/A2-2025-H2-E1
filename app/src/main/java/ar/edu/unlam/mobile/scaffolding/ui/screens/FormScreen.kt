package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.FormUserInput
import ar.edu.unlam.mobile.scaffolding.ui.components.SnackbarVisualsWithError
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.UserViewModel
import ar.edu.unlam.mobile.scaffolding.utils.validateForm
import kotlinx.coroutines.launch

data class ValidationResult(
    val isValid: Boolean,
    val message: String,
)

@Composable
fun FormScreen(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel(),
    navController: NavController,
) {
    val scope = rememberCoroutineScope()

    val registerState by viewModel.registerState.collectAsState()
    val emailDuplicated by viewModel.emailDuplicated.collectAsState()

    LaunchedEffect(registerState) {
        registerState?.let { response ->
            val token = response.token
            if (!token.isNullOrEmpty()) {
                navController.navigate("feedTuitScreen")
                snackbarHostState.showSnackbar(
                    SnackbarVisualsWithError("Signed up successfully!", false),
                )
            }
        }
    }

    LaunchedEffect(emailDuplicated) {
        if (emailDuplicated) {
            snackbarHostState.showSnackbar(
                SnackbarVisualsWithError("Email duplicated", true),
            )
        }
    }

    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        var nameState by remember { mutableStateOf("") }
        var emailState by remember { mutableStateOf("") }
        var passwordState by remember { mutableStateOf("") }
        var repeatPasswordState by remember { mutableStateOf("") }

        val enabled =
            nameState.isNotBlank() &&
                emailState.isNotBlank() &&
                passwordState.isNotBlank() &&
                repeatPasswordState.isNotBlank()

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(4.dp))

            FormUserInput(
                title = "name",
                text = nameState,
                onTextChange = { nameState = it },
            )

            FormUserInput(
                title = "email",
                text = emailState,
                onTextChange = { emailState = it },
            )

            FormUserInput(
                title = "password",
                text = passwordState,
                onTextChange = { passwordState = it },
            )

            FormUserInput(
                title = "repeatPassword",
                text = repeatPasswordState,
                onTextChange = { repeatPasswordState = it },
            )

            Spacer(Modifier.height(8.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                content = { Text("Clear name") },
                onClick = {
                    nameState = ""
                },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.primary,
                    ),
            )

            Spacer(Modifier.height(8.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                content = { Text("Register") },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.White,
                    ),
                onClick = {
                    val res =
                        validateForm(
                            nameState,
                            emailState,
                            passwordState,
                            repeatPasswordState,
                            type = "register",
                        )
                    if (res.isValid) {
                        viewModel.register(
                            name = nameState,
                            password = passwordState,
                            email = emailState,
                        )
                    }
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            SnackbarVisualsWithError(res.message, !res.isValid),
                        )
                    }
                },
                enabled = enabled,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "¿Do you have an account?")
                Spacer(modifier = Modifier.width(5.dp))
                TextButton(
                    onClick = { navController.navigate("logInScreen") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                ) {
                    Text("Login")
                }
            }
        }
    }
}
