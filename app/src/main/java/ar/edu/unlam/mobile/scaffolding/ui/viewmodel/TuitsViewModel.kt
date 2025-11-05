package ar.edu.unlam.mobile.scaffolding.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.data.repositories.TuitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FeedUIState {
    object Loading : FeedUIState

    data class Success(
        val data: List<Tuit> = emptyList(),
    ) : FeedUIState

    data class Error(
        val message: Exception,
    ) : FeedUIState
}

@HiltViewModel
class TuitsViewModel
    @Inject
    constructor(
        private val tuitsRepo: TuitsRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<FeedUIState>(FeedUIState.Loading)
        val uiState = _uiState.asStateFlow()

        fun getAllTuits() =
            viewModelScope.launch {
                tuitsRepo
                    .getTuits()
                    .onSuccess { listOfTuits ->
                        _uiState.update {
                            FeedUIState.Success(data = listOfTuits)
                        }
                    }.onFailure { exception ->
                        _uiState.update { FeedUIState.Error(message = exception) }
                    }
            }

        fun removeLikes(tuit: Tuit) {
            viewModelScope.launch {
                tuitsRepo
                    .removeTuitLike(key = tuit)
                    .onSuccess {
                        Log.i("remove_likes_onSuccess", "${it.liked}")
                    }.onFailure { it ->
                        Log.i("remove_likes_onFailure", "$it")
                    }
            }
            updateTuit(tuit)
            Log.i("remove_likes_onSuccess", "${tuit.liked}")
        }

        fun addLikes(tuit: Tuit) {
            viewModelScope.launch {
                tuitsRepo
                    .updateTuitLikes(key = tuit)
                    .onSuccess {
                        Log.i("add_likes_OnSuccess", "${it.likes}")
                    }.onFailure { it ->
                        Log.i("add_likes_OnFailure", "$it")
                    }
            }
            updateTuit(tuit)
            Log.i("likes_onSuccess", "${tuit.liked}")
        }

        fun updateTuit(tuit: Tuit) {
            val currentState = _uiState.value
            if (currentState !is FeedUIState.Success) return

            val feedTuits = currentState.data.toMutableList()
            val index = feedTuits.indexOfFirst { it.id == tuit.id }
            if (index != -1) {
                val tuitLiked = feedTuits[index]
                val newLikes = if (!tuit.liked) tuitLiked.likes + 1 else tuitLiked.likes - 1
                feedTuits[index] = tuitLiked.copy(liked = !tuit.liked, likes = newLikes)
            }
            currentState.copy(data = feedTuits.toList())
            _uiState.value = FeedUIState.Success(data = feedTuits.toList())
        }
    }
