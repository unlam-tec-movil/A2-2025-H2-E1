package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.data.repositories.events.FeedTuitsFilter
import ar.edu.unlam.mobile.scaffolding.data.repositories.events.TuitAction
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomDivider
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomErrorView
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomLoadingState
import ar.edu.unlam.mobile.scaffolding.ui.components.tuit.TuitCard
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.FeedUIState
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.FeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedTuitsScreen(
    feedViewModel: FeedViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by feedViewModel.uiState.collectAsStateWithLifecycle()
    val feedTuitsState by feedViewModel.feedTuitsState.collectAsState()
    val usersSavedState by feedViewModel.savedUsersState.collectAsState()
    var currentFilter by remember { mutableStateOf<FeedTuitsFilter>(FeedTuitsFilter.AllTuits) }
    // al usar remember(key) le indico que recuerde el resutlado del codigo en las llaves siguientes
    // y que solo vuelva a calcular t odo si la key cambia
    val usersSavedMap =
        remember(usersSavedState) {
            usersSavedState.map { it.authorId }.toSet()
        }
    val tuitsSavedState by feedViewModel.savedTuits.collectAsState()
    val tuitsSavedMap =
        remember(tuitsSavedState) {
            tuitsSavedState
                .map {
                    it.tuitId
                }.toSet()
        }
    val filteredTuits =
        remember(
            currentFilter,
            feedTuitsState,
            usersSavedState,
            tuitsSavedState,
        ) {
            when (currentFilter) {
                FeedTuitsFilter.AllTuits -> {
                    feedTuitsState.data.toList()
                }

                FeedTuitsFilter.FavoriteTuits -> {
                    feedTuitsState.data.filter { tuit ->
                        tuitsSavedMap.contains(tuit.id)
                    }
                }

                FeedTuitsFilter.TuitsMadeByFavoriteUsers -> {
                    feedTuitsState.data.filter { tuit ->
                        usersSavedMap.contains(tuit.authorId)
                    }
                }
            }
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
                    actions = {
                        var menuExpanded by remember { mutableStateOf((false)) }
                        Box {
                            IconButton(
                                onClick = { menuExpanded = true },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = "filter List Clickable Icon",
                                )
                                DropdownMenu(
                                    expanded = menuExpanded,
                                    onDismissRequest = { menuExpanded = false },
                                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
                                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                                ) {
                                    DropdownMenuItem(
                                        text = { Text(text = "All tuits") },
                                        onClick = {
                                            currentFilter = FeedTuitsFilter.AllTuits
                                            menuExpanded = false
                                        },
                                    )
                                    CustomDivider()

                                    DropdownMenuItem(
                                        text = { Text(text = "Only made by favorite users") },
                                        onClick = {
                                            currentFilter = FeedTuitsFilter.TuitsMadeByFavoriteUsers
                                            menuExpanded = false
                                        },
                                    )
                                    CustomDivider()
                                    DropdownMenuItem(
                                        text = { Text(text = "Only favorite tuits") },
                                        onClick = {
                                            currentFilter = FeedTuitsFilter.FavoriteTuits
                                            menuExpanded = false
                                        },
                                    )
                                }
                            }
                        }
                    },
                )
            }) { paddingValues ->
                LazyColumn(Modifier.padding(paddingValues = paddingValues)) {
                    itemsIndexed(items = filteredTuits) { index, tuit ->
                        val isUserSaved = usersSavedMap.contains(tuit.authorId)
                        val isTuitSaved = tuitsSavedMap.contains(tuit.id)
                        TuitCard(
                            tuit = tuit,
                            userIsSaved = isUserSaved,
                            isTuitSaved = isTuitSaved,
                            navigateToTuitScreen = {
                                navController.navigate("tuitScreen/${tuit.id}")
                            },
                            onLikeChanged = {
                                feedViewModel.onLikedChange(it)
                            },
                            onBookmarkClick = { tuitAction ->
                                when (tuitAction) {
                                    is TuitAction.FavoriteTuit -> {
                                        feedViewModel.favoriteTuitManagment(
                                            isTuitSaved = isTuitSaved,
                                            tuit = tuit,
                                        )
                                    }

                                    is TuitAction.FavoriteUser -> {
                                        feedViewModel.favoriteUsersManagment(
                                            isUserSaved,
                                            tuit,
                                        )
                                    }
                                }
                            },
                            replies = 0,
                        )
                        CustomDivider()
                    }
                }
            }
        }
    }
}
