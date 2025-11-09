package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.PostScreenViewModel

const val POST_ROUTE = "postTuiter"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    viewModel: PostScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val postText by viewModel.postText.collectAsState()
    val borradores by viewModel.borradorState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Carga del feed al entrar
    LaunchedEffect(Unit) {
        viewModel.loadFeed()
    }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
    ) {
        Text("New Post")
        OutlinedTextField(
            value = postText,
            onValueChange = { nuevoTexto ->
                viewModel.onTextChanged(nuevoTexto)
            },
            label = { Text("Escribe tu post...") },
            modifier = Modifier.fillMaxWidth(),
        )
        Row {
            Box(
                modifier = Modifier.padding(5.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Button(onClick = {
                    var boolean = false
                    if (postText.isNotEmpty()) {
                        viewModel.textoAGuardar()
                        boolean = true
                    }
                    if (boolean) {
                        Toast.makeText(context, "Borrador guardado", Toast.LENGTH_LONG).show()
                    } else {
                        Toast
                            .makeText(context, "No se pudo guardar el borrador", Toast.LENGTH_LONG)
                            .show()
                    }
                }) {
                    Text("Guardar Borrador")
                }
            }

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Button(onClick = {
                    var boolean = false
                    if (postText.isNotEmpty()) {
                        viewModel.textoATuitear()
                        boolean = true
                    }
                    if (boolean) {
                        Toast.makeText(context, "Tuit creado", Toast.LENGTH_LONG).show()
                    } else {
                        Toast
                            .makeText(context, "No se pudo tuitear", Toast.LENGTH_LONG)
                            .show()
                    }
                }) {
                    Text("Publicar")
                }
            }
        }

        /*Text("Borradores")
        LazyColumn(
            modifier =
                Modifier
                    .padding(16.dp),
        ) {
            items(borradores) { borrador ->
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "${borrador.borrador}")
                }
            }
        }*/

        Spacer(modifier = Modifier.height(24.dp))

        // Estado del feed
        when (val state = uiState) {
            is PostScreenViewModel.FeedState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is PostScreenViewModel.FeedState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
            is PostScreenViewModel.FeedState.Success -> {
                Text("Tus tuits", style = MaterialTheme.typography.titleMedium)
                LazyColumn(
                    modifier = Modifier.padding(top = 8.dp),
                ) {
                    items(state.tuits) { tuit ->
                        Text(
                            text = tuit.message,
                            modifier = Modifier.padding(vertical = 4.dp),
                        )
                    }
                }
            }
            PostScreenViewModel.FeedState.Idle -> { /* nada todavía */ }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de borradores
        Text("Borradores", style = MaterialTheme.typography.titleMedium)
        LazyColumn(
            modifier = Modifier.padding(top = 8.dp),
        ) {
            items(borradores) { borrador ->
                Text(text = borrador.borrador)
            }
        }
    }
}
