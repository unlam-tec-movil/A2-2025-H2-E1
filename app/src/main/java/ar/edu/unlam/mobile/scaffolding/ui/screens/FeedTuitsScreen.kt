package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.ui.components.ItemTuit
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.TuitsViewModel

// const val FEED_SCREEN_ROUTE = "feedScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedTuitsScreen(tuitsViewModel: TuitsViewModel = hiltViewModel()) {
    val tuits by tuitsViewModel.tuitState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Feed",
            )
        })
    }) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier
                    .padding(paddingValues = paddingValues)
                    .padding(16.dp),
        ) {
            items(tuits) { tuit ->
                ItemTuit(tuit)
            }
        }
    }
}
