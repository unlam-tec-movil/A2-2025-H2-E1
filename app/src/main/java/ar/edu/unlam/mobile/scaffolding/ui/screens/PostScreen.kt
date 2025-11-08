package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.TuitsBorrador
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.PostScreenViewModel

const val POST_ROUTE = "postTuiter"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    viewModel: PostScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val message by viewModel.postState.collectAsState()
    val borradores by viewModel.borradorState.collectAsState()
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
                    if (message.isNotEmpty()) {
                        viewModel.textoAGuardar(message)
                        Toast
                            .makeText(
                                context,
                                "Borrador guardado",
                                Toast.LENGTH_LONG,
                            ).show()
                    } else {
                        Toast
                            .makeText(
                                context,
                                "Borrador vacio",
                                Toast.LENGTH_LONG,
                            ).show()
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
                    if (message.isNotEmpty()) {
                        viewModel.textoATuitear(message)
                        Toast
                            .makeText(
                                context,
                                "Tuit creado",
                                Toast.LENGTH_LONG,
                            ).show()
                        // esto refresca la pantalla anterior
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("refresco", true)
                        // vuelve al feed
                        navController.popBackStack()
                        // navController.navigate("feedTuitScreen")
                    } else {
                        Toast
                            .makeText(
                                context,
                                "Tuit vacio",
                                Toast.LENGTH_LONG,
                            ).show()
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
                    CardParaBorrador(borrador, viewModel)
                }
            }
        }
    }
}

@Composable
fun CardParaBorrador(
    contenido: TuitsBorrador,
    viewModel: PostScreenViewModel = hiltViewModel(),
) {
    val contexto = LocalContext.current
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    val mensajeDelBorrador =
                        obtenerBorrador(
                            contenido,
                        )
                    Toast
                        .makeText(
                            contexto,
                            "$mensajeDelBorrador",
                            Toast.LENGTH_LONG,
                        ).show()
                    viewModel.onTextChanged(mensajeDelBorrador)
                }),
    ) {
        Text(contenido.textoBorrador)
    }
}

fun obtenerBorrador(borrador: TuitsBorrador): String = borrador.textoBorrador

/*@Preview
@Composable
fun CardParaBorradorPreview() {
    CardParaBorrador()
}*/
