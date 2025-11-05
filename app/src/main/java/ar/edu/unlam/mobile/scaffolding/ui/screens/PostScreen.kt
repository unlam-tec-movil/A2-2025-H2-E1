package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val message by viewModel.postState.collectAsState()
    val borradores by viewModel.borradorState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
    ) {
        Text("Nuevo Post")
        OutlinedTextField(
            value = message,
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
                    if (message.isNotEmpty()) {
                        viewModel.textoAGuardar(message)
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
                    if (message.isNotEmpty()) {
                        viewModel.textoATuitear(message)
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
        Text("Borradores")
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
        }
    }
}
