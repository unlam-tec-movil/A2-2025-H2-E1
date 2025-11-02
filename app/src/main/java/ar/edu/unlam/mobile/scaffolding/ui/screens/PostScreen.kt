package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.PostScreenViewModel

const val POST_ROUTE = "postTuiter"

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    viewModel: PostScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val message by viewModel.postState.collectAsState()

    Column(modifier = Modifier.padding(20.dp)) {
        Text("Nuevo Post")
        OutlinedTextField(
            value = message,
            onValueChange = { nuevoTexto ->
                viewModel.onTextChanged(nuevoTexto)
            },
            label = { Text("Escribe tu post...") },
        )
        Row {
            Box(modifier = Modifier.padding(5.dp)) {
                // modifier = Modifier.align(Alignment.Start)) {
                Button(onClick = {}) {
                    Text("Guardar Borrador")
                }
            }

            Box(modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 5.dp)) {
                Button(onClick = { viewModel.textoATuitear(message) }) {
                    Text("Publicar")
                }
            }
        }
    }
}

// @Preview(showBackground = true)
// @Composable
// fun PostScreenPreview(){
//    PostScreen()
// }
