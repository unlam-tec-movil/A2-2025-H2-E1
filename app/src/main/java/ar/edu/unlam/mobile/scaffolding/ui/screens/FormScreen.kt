package ar.edu.unlam.mobile.scaffolding.ui.screens

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.unlam.mobile.scaffolding.ui.components.SnackbarVisualsWithError
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
) {
    val scope = rememberCoroutineScope()
    Surface(modifier = modifier) {
        val nameState = rememberTextFieldState()
        val emailState = rememberTextFieldState()
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
                state = rememberTextFieldState(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )
            TextField(
                label = { Text("Repetir Contraseña") },
                state = rememberTextFieldState(),
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
                    val res = validateForm(nameState.text.toString(), emailState.text.toString())
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            SnackbarVisualsWithError(res.message, !res.isValid),
                        )
                    }
                },
            )
        }
    }
}

@Preview
@Composable
fun FormScreenPreview() {
    val snackBarHostState = remember { SnackbarHostState() }
    FormScreen(snackBarHostState)
}
