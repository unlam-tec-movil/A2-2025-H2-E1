package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomErrorView
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomIcon
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomLoadingState
import ar.edu.unlam.mobile.scaffolding.ui.components.DefaultText
import ar.edu.unlam.mobile.scaffolding.ui.components.tuit.TuitCard
import ar.edu.unlam.mobile.scaffolding.ui.components.tuitDetail.TuitDetail
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.FeedUIState
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.TuitsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TuitScreen(
    tuitId: Int,
    tuitsViewModel: TuitsViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by tuitsViewModel.uiState.collectAsStateWithLifecycle()
    val feedTuitsState by tuitsViewModel.feedTuitsState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = tuitId) {
        tuitsViewModel.getTuitById(tuitId)
        tuitsViewModel.getTuitReplies(tuitId)
        tuitsViewModel.getAllTuits()
    }

    when (val state = uiState) {
        is FeedUIState.Error -> CustomErrorView(state.message.toString())
        is FeedUIState.Loading -> CustomLoadingState()
        is FeedUIState.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            DefaultText(title = "Post")
                        },
                        colors =
                            TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background,
                            ),
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.navigateUp()
                            }) {
                                CustomIcon(
                                    icon = Icons.Default.ArrowBack,
                                    modifier = Modifier.size(25.dp),
                                )
                            }
                        },
                    )
                },
            ) { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues = paddingValues)) {
                    TuitDetail(tuit = feedTuitsState.tuit, onFavoriteChanged = {
                        tuitsViewModel.onFavoriteChange(feedTuitsState.tuit)
                    }, onclickReply = {
                        navController.navigate("replyScreen/${feedTuitsState.tuit.id}")
                    })
                    LazyColumn {
                        itemsIndexed(items = feedTuitsState.replies) { index, tuit ->
                            TuitCard(
                                tuit = tuit,
                                navigateToTuitScreen = {
                                    navController.navigate("tuitScreen/${tuit.id}")
                                },
                                onFavoriteChanged = {
                                    tuitsViewModel.onFavoriteChange(tuit)
                                },
                            )
                            CustomDivider()
                        }
                    }
                }
            }
        }
    }
}
