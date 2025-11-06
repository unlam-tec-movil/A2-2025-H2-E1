package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserInput(title: String,
              modifier: Modifier = Modifier,
              text: String,
              onTextChange: (String) -> Unit
) {
    Card(
        modifier = modifier.width(250.dp).height(55.dp).fillMaxSize(),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
    ) {

        when (title) {
            "name" -> TextField(
                value = text,
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        text = "Name",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.inversePrimary,    // Fondo cuando está enfocado
                    unfocusedContainerColor = MaterialTheme.colorScheme.inversePrimary,  // Fondo cuando NO está enfocado
                    focusedTextColor = Color.White,       // Texto ingresado
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.LightGray, // Placeholder enfocado
                    unfocusedPlaceholderColor = Color.LightGray, // Placeholder desenfocado
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            "email" -> TextField(
                value = text,
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.inversePrimary,    // Fondo cuando está enfocado
                    unfocusedContainerColor = MaterialTheme.colorScheme.inversePrimary,  // Fondo cuando NO está enfocado
                    focusedTextColor = Color.White,       // Texto ingresado
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.LightGray, // Placeholder enfocado
                    unfocusedPlaceholderColor = Color.LightGray, // Placeholder desenfocado
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            "password" -> TextField(
                value = text,
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.inversePrimary,    // Fondo cuando está enfocado
                    unfocusedContainerColor = MaterialTheme.colorScheme.inversePrimary,  // Fondo cuando NO está enfocado
                    focusedTextColor = Color.White,       // Texto ingresado
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.LightGray, // Placeholder enfocado
                    unfocusedPlaceholderColor = Color.LightGray, // Placeholder desenfocado
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            "repeatPassword" -> TextField(
                value = text,
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        text = "RepeatPassword",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.inversePrimary,    // Fondo cuando está enfocado
                    unfocusedContainerColor = MaterialTheme.colorScheme.inversePrimary,  // Fondo cuando NO está enfocado
                    focusedTextColor = Color.White,       // Texto ingresado
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.LightGray, // Placeholder enfocado
                    unfocusedPlaceholderColor = Color.LightGray, // Placeholder desenfocado
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}