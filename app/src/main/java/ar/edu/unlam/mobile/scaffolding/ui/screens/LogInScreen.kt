package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.R.attr.singleLine
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuiterDao
import ar.edu.unlam.mobile.scaffolding.ui.components.SnackbarVisualsWithError
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.LogInViewModel
import ar.edu.unlam.mobile.scaffolding.utils.validateForm
import kotlinx.coroutines.launch
import org.w3c.dom.Text


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    logInViewModel: LogInViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()

    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            state = emailState,
            supportingText = { Text(text = "email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
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
                    snackbarHostState.showSnackbar(
                        visuals = SnackbarVisualsWithError(
                            res.message,
                            !res.isValid
                        )
                    )
                    if (res.isValid) {
                        logInViewModel.logInVM(
                            email = emailState.text.toString(),
                            password = passwordState.text.toString()
                        )

                    }
                }
            }
        ) { Text(text = "iniciar sesion") }
    }
}


