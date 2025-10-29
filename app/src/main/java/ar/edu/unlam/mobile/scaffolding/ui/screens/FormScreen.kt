package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.RetrofitInstance
import ar.edu.unlam.mobile.scaffolding.ui.components.SnackbarVisualsWithError
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext


@Composable
fun FormScreen(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val registerState by viewModel.registerState.collectAsState()

    // 👇 Se ejecuta cuando llega una respuesta del registro
    LaunchedEffect(registerState) {

        registerState?.let { response ->
            val token = response.token
            if (!token.isNullOrEmpty()) {
                RetrofitInstance.setUserToken(token)
                //navController.navigate(HOME_SCREEN_ROUTE)

                Toast.makeText(context, response.token, Toast.LENGTH_SHORT).show()

            }
        }

    }

    Surface(modifier = modifier) {

        val nameState = rememberTextFieldState()
        val emailState = rememberTextFieldState()
        val passwordState = rememberTextFieldState()
        val repeatPasswordState = rememberTextFieldState()

        Column {
            TextField(
                label = { Text("Nombre") },
                state = nameState,
                supportingText = { Text("Ingrese su nombre completo") },
            )
            TextField(
                label = { Text("Email") },
                state = emailState,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            TextField(
                label = { Text("Contraseña") },
                state = passwordState,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )
            TextField(
                label = { Text("Repetir Contraseña") },
                state = repeatPasswordState,
            )
            Button(
                content = { Text("Limpiar Name") },
                onClick = {
                    nameState.clearText()
                },
            )
            Button(
                content = { Text("Enviar") },
                onClick = {
                    val res = validateForm(
                        nameState.text.toString(),
                        emailState.text.toString(),
                        passwordState.text.toString(),
                        repeatPasswordState.text.toString()
                    )
                    if (res.isValid){

                        viewModel.register(
                            name = nameState.text.toString(),
                            password = passwordState.text.toString(),
                            email = emailState.text.toString()
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

const val FORM_ROUTE = "form"

data class ValidationResult(
    val isValid: Boolean,
    val message: String,
)

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
        message = "Formulario válido 😎",
    )
}




/*
@Preview
@Composable
fun FormScreenPreview() {
    val snackBarHostState = remember { SnackbarHostState() }
    FormScreen(snackBarHostState)
}
 */
