package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.GradientBackground
import ar.edu.unlam.mobile.scaffolding.ui.components.SnackbarVisualsWithError
import ar.edu.unlam.mobile.scaffolding.ui.components.UserInput
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.UserViewModel
import ar.edu.unlam.mobile.scaffolding.utils.validateForm
import kotlinx.coroutines.launch

data class ValidationResult(
    val isValid: Boolean,
    val message: String
)

@Composable
fun FormScreen(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel(),
    navController: NavController,
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val registerState by viewModel.registerState.collectAsState()

    // 👇 Se ejecuta cuando llega una respuesta del registro
    LaunchedEffect(registerState) {

        registerState?.let { response ->
            val token = response.token
            if (!token.isNullOrEmpty()) {
                Toast.makeText(context, response.token, Toast.LENGTH_SHORT).show()
                navController.navigate("feedTuitScreen")
            }
        }

    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        GradientBackground()

        var nameState by remember { mutableStateOf("") }
        var emailState by remember { mutableStateOf("") }
        var passwordState by remember { mutableStateOf("") }
        var repeatPasswordState by remember { mutableStateOf("") }


        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

            Text(
                text = "Tuiter",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 50.sp),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            UserInput(
                title = "name",
                text = nameState,
                onTextChange = { nameState = it })

            UserInput(
                title = "email",
                text = emailState,
                onTextChange = { emailState = it })

            UserInput(
                title = "password",
                text = passwordState,
                onTextChange = { passwordState = it })

            UserInput(
                title = "repeatPassword",
                text = repeatPasswordState,
                onTextChange = { repeatPasswordState = it })

            Spacer(Modifier.height(8.dp))

            Button(
                content = { Text("Clear name") },
                onClick = {
                    nameState = ""
                },
            )

            Spacer(Modifier.height(8.dp))

            Button(
                content = { Text("Register") },
                onClick = {
                    val res = validateForm(
                        nameState,
                        emailState,
                        passwordState,
                        repeatPasswordState
                    )
                    if (res.isValid){

                        viewModel.register(
                            name = nameState,
                            password = passwordState,
                            email = emailState,
                            context = context
                        )

                    }
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            SnackbarVisualsWithError(res.message, !res.isValid),
                        )
                    }
                }
            )
        }


    }
}

fun validateForm(
    name: String,
    email: String,
    password: String,
    repeatPassword: String
): ValidationResult {
    if (name.isEmpty()) {
        return ValidationResult(
            isValid = false,
            message = "El nombre no puede estar vacío",
        )
    }

    if (!email.contains("@")) {
        return ValidationResult(
            isValid = false,
            message = "El email debe ser válido",
        )
    }

    if (password != repeatPassword){
        return ValidationResult(
            isValid = false,
            message = "Las contraseñas deben coincidir",
        )
    }
    return ValidationResult(
        isValid = true,
        message = "Formulario válido ",
    )
}


