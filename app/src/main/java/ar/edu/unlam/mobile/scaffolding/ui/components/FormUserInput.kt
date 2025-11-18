package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

@Composable
fun FormUserInput(
    title: String,
    text: String,
    onTextChange: (String) -> Unit,
) {
    when (title) {
        "name" ->
            OutlinedTextField(
                value = text,
                textStyle = MaterialTheme.typography.bodyLarge,
                label = { Text("Name") },
                singleLine = true,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        text = "Name",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )

        "email" ->
            OutlinedTextField(
                value = text,
                label = { Text("Email") },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )

        "password" -> {
            var passwordVisible by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = text,
                label = { Text("Password") },
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                },
                visualTransformation =
                    if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                trailingIcon = {
                    val image =
                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        "repeatPassword" -> {
            var passwordVisible by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = text,
                label = { Text("Repeat Password") },
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        text = "Repeat Password",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                },
                visualTransformation =
                    if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                trailingIcon = {
                    val image =
                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
