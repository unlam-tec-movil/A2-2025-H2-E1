package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomErrorView
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomIcon
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomLoadingState
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomMultilineTextField
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomSubtitle
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.FeedUIState
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.TuitsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyScreen(
    tuitId: Int,
    navController: NavController,
    tuitsViewModel: TuitsViewModel = hiltViewModel(),
) {
    val uiState by tuitsViewModel.uiState.collectAsStateWithLifecycle()
    val feedTuitsState by tuitsViewModel.feedTuitsState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = tuitId) {
        tuitsViewModel.getTuitById(tuitId)
    }
    when (val state = uiState) {
        is FeedUIState.Error -> CustomErrorView(state.message.toString())
        is FeedUIState.Loading -> CustomLoadingState()
        is FeedUIState.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {},
                        colors =
                            TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background,
                            ),
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
                            Button(
                                modifier =
                                    Modifier
                                        .padding(end = 10.dp)
                                        .width(120.dp)
                                        .height(35.dp),
                                onClick = {
                                    tuitsViewModel.addReply(
                                        tuit = feedTuitsState.tuit,
                                        message = feedTuitsState.replyMessage,
                                    )
                                    navController.navigateUp()
                                },
                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                    ),
                            ) {
                                CustomSubtitle(title = "Publicar", color = Color.White)
                            }
                        },
                    )
                },
            ) { paddingValues ->
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(paddingValues = paddingValues).padding(20.dp),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(45.dp)
                                .background(Color.LightGray)
                                .clip(shape = CircleShape),
                    )
                    CustomMultilineTextField(
                        value = feedTuitsState.replyMessage,
                        onValueChange = { tuitsViewModel.onReplyChanged(it) },
                        hintText = "¿Qué estas pensando?",
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 15,
                    )
                }
            }
        }
    }
}
