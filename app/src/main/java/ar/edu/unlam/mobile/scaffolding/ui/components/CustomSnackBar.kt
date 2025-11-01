package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackBar(hostState: SnackbarHostState) {
    SnackbarHost(hostState = hostState) { data ->
        // custom snackbar with the custom action button color and border
        val isError = (data.visuals as? SnackbarVisualsWithError)?.isError ?: false
        val buttonColor =
            if (isError) {
                ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error,
                )
            } else {
                ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.inversePrimary,
                )
            }

        Snackbar(
            modifier =
                Modifier
                    .border(2.dp, MaterialTheme.colorScheme.secondary)
                    .padding(12.dp),
            action = {
                TextButton(
                    onClick = { if (isError) data.dismiss() else data.performAction() },
                    colors = buttonColor,
                ) {
                    Text(data.visuals.actionLabel ?: "")
                }
            },
        ) {
            Text(data.visuals.message)
        }
    }
}
