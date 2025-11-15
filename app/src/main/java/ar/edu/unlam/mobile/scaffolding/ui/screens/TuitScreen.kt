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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomErrorView
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomIcon
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomLoadingState
import ar.edu.unlam.mobile.scaffolding.ui.components.DefaultText
import ar.edu.unlam.mobile.scaffolding.ui.components.tuit.TuitCard
import ar.edu.unlam.mobile.scaffolding.ui.components.tuitDetail.TuitDetail
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.FeedUIState
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.FeedViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TuitScreen(
    tuitId: Int,
    feedViewModel: FeedViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by feedViewModel.uiState.collectAsStateWithLifecycle()
    val feedTuitsState by feedViewModel.feedTuitsState.collectAsState()
    val usersSavedState by feedViewModel.savedUsersState.collectAsState()
    val usersSavedMap =
        remember(usersSavedState) {
            usersSavedState.map { it.authorId }.toSet()
        }

    LaunchedEffect(key1 = tuitId) {
        feedViewModel.getTuitById(tuitId)
        delay(100)
        feedViewModel.getTuitReplies(tuitId)
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
                    TuitDetail(
                        tuit = feedTuitsState.tuit,
                        onLikeChanged = {
                            feedViewModel.onLikedChange(it)
                        },
                        onclickReply = {
                            navController.navigate("replyScreen/${feedTuitsState.tuit.id}")
                        },
                    )

                    if (feedTuitsState.replies != emptyList<Tuit>()) {
                        LazyColumn {
                            itemsIndexed(items = feedTuitsState.replies) { index, tuit ->
                                var isSaved = usersSavedMap.contains(tuit.authorId)
                                TuitCard(
                                    tuit = tuit,
                                    navigateToTuitScreen = {
                                        navController.navigate("tuitScreen/${tuit.id}")
                                    },
                                    onLikeChanged = {
                                        feedViewModel.onLikedChange(it)
                                    },
                                    onBookmarkClick = {},
                                    userIsSaved = isSaved,
                                )
                                CustomDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}
