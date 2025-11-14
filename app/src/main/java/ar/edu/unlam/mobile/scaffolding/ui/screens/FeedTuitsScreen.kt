package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomAvatar
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomErrorView
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomLoadingState
import ar.edu.unlam.mobile.scaffolding.ui.components.tuit.BottomRow
import ar.edu.unlam.mobile.scaffolding.ui.components.tuit.MiddleRow
import ar.edu.unlam.mobile.scaffolding.ui.components.tuit.TopRow
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.FeedUIState
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.FeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedTuitsScreen(
    feedViewModel: FeedViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by feedViewModel.uiState.collectAsStateWithLifecycle()

    val usersSavedState by feedViewModel.savedUsersState.collectAsState()
    // al usar remember(key) le indico que recuerde el resulado del codigo en las llaves siguientes
    // y que solo vuelva a calcular t odo si la key cambia
    val usersSavedMap =
        remember(usersSavedState) {
            usersSavedState.map { it.authorId }.toSet()
        }

    // escucha el refresco del PostScreen
    val navBackStackEntry = navController.currentBackStackEntry
    val refresco =
        navBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Boolean>("refresco")
            ?.observeAsState(initial = false)
    LaunchedEffect(key1 = Unit, key2 = refresco?.value) {
        if (refresco?.value == true) {
            feedViewModel.getAllTuits()
            navBackStackEntry.savedStateHandle.set("refresco", false)
        } else {
            feedViewModel.getAllTuits()
        }
    }
    when (val state = uiState) {
        is FeedUIState.Error -> CustomErrorView(state.message.toString())
        is FeedUIState.Loading -> CustomLoadingState()
        is FeedUIState.Success -> {
            Scaffold(topBar = {
                TopAppBar(
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                    title = {
                        Box(
                            Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(R.drawable.tuiter_img),
                                contentDescription = null,
                            )
                        }
                    },
                )
            }) { paddingValues ->
                LazyColumn(Modifier.padding(paddingValues = paddingValues)) {
                    itemsIndexed(items = state.data) { index, tuit ->
                        var isSaved: Boolean = usersSavedMap.contains(tuit.authorId)
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                                .padding(top = 5.dp),
                        ) {
                            CustomAvatar(tuit = tuit)
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                                    .padding(top = 3.dp),
                            ) {
                                TopRow(tuit)
                                MiddleRow(tuit)
                                BottomRow(
                                    tuit,
                                    onLikeClick = {
                                        if (tuit.liked) {
                                            feedViewModel.removeLikes(tuit)
                                        } else {
                                            feedViewModel.addLikes(tuit)
                                        }
                                    },
                                    onBookmarkClick = {
                                        feedViewModel.favoriteUsersManagment(isSaved, tuit)
                                    },
                                    isSaved = usersSavedMap.contains(tuit.authorId),
                                )
                            }
                        }
                        CustomDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun CustomDivider() {
    HorizontalDivider(
        Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondary,
        thickness = 0.25f.dp,
    )
}
