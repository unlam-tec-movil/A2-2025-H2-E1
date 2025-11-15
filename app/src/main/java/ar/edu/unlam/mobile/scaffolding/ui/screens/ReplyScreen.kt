package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomAvatar
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomErrorView
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomIcon
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomLoadingState
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomMultilineTextField
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomSubtitle
import ar.edu.unlam.mobile.scaffolding.ui.components.DefaultText
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.EditUserViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.FeedUIState
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.FeedViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.PostScreenViewModel
import ar.edu.unlam.mobile.scaffolding.utils.Constants.DEFAULT_URL_IMAGE
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyScreen(
    tuitId: Int,
    navController: NavController,
    tuitsViewModel: FeedViewModel = hiltViewModel(),
    postViewModel: PostScreenViewModel = hiltViewModel(),
    editUserViewModel: EditUserViewModel = hiltViewModel(),
) {
    val userProfileDataState by editUserViewModel.userProfileDataState.collectAsState()
    val message by postViewModel.postState.collectAsState()
    val uiState by tuitsViewModel.uiState.collectAsStateWithLifecycle()
    val feedTuitsState by tuitsViewModel.feedTuitsState.collectAsState()
    val borradores by postViewModel.borradorState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = tuitId) {
        tuitsViewModel.getTuitById(tuitId)
        delay(1000)
    }

    when (val state = uiState) {
        is FeedUIState.Error -> CustomErrorView(state.message.toString())
        is FeedUIState.Loading -> CustomLoadingState()
        is FeedUIState.Success -> {
            Scaffold(
                // Barra superior de la pantalla
                topBar = {
                    TopAppBar(
                        title = {},
                        colors =
                            TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background,
                            ),
                        // Icono en cruz clickleable que regresa a la pantalla anterior
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.navigateUp()
                            }) {
                                CustomIcon(
                                    icon = Icons.Default.Close,
                                    modifier = Modifier.size(25.dp),
                                )
                            }
                        },
                        actions = {
                            // texto clicleable de "guardar borrador" que solo aparece
                            // si el campo de texto no esta vacio
                            if (message.isNotEmpty()) {
                                Text(
                                    text = "Guardar borrador",
                                    color = MaterialTheme.colorScheme.tertiary,
                                    modifier =
                                        Modifier.clickable(
                                            onClick = {
                                                if (message.isNotEmpty()) {
                                                    postViewModel.textoAGuardar(message)
                                                    postViewModel.limpiarCampoDeTexto()
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
                                            },
                                        ),
                                )
                            }

                            Spacer(modifier = Modifier.width(5.dp))
                            // Boton de publicar
                            Button(
                                enabled = message.isNotEmpty(),
                                modifier =
                                    Modifier
                                        .width(110.dp)
                                        .height(35.dp),
                                onClick = {
                                    tuitsViewModel.addReply(
                                        tuit = feedTuitsState.tuit,
                                        message = message,
                                    )
                                    postViewModel.deleteDraft(message)
                                    navController.navigateUp()
                                },
                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                    ),
                            ) {
                                CustomSubtitle(
                                    title = "Publicar",
                                    fontSize = 14,
                                    color = Color.White,
                                )
                            }
                            Spacer(Modifier.width(10.dp))
                        },
                    )
                },
            ) { paddingValues ->

                Column(
                    Modifier
                        .padding(paddingValues = paddingValues)
                        .padding(20.dp),
                ) {
                    // Text en la parte susperior del avatar y textField que le muestra al usuario a que autor le responde
                    DefaultText(title = "En respuesta a @${feedTuitsState.tuit.author}")
                    Spacer(Modifier.height(10.dp))
                    // Fila que contiene el icono del usuario que responde y
                    Row(
                        verticalAlignment = Alignment.Top,
                    ) {
                        // avatar del usuario
                        CustomAvatar(
                            avatarUrl = userProfileDataState?.avatarUrl ?: DEFAULT_URL_IMAGE,
                        )
                        // textfield donde escribe el usuario
                        CustomMultilineTextField(
                            value = message,
                            onValueChange = {
//                                tuitsViewModel.onReplyChanged(it)
                                postViewModel.onTextChanged(it)
                            },
                            hintText = "Responder",
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 15,
                        )
                    }
                    // Columna que lista borradores
                    Spacer(Modifier.height(20.dp))
                    Text("Borradores", style = MaterialTheme.typography.titleMedium)
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        items(borradores) { borrador ->
                            Column(modifier = Modifier.padding(16.dp)) {
                                CardParaBorrador(borrador, onItemClick = { textoDelBorrador ->
                                    postViewModel.onTextChanged(textoDelBorrador)
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}
