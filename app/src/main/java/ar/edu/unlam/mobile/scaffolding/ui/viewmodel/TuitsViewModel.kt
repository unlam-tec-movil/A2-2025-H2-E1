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

    object Success : FeedUIState

    data class Error(
        val message: Exception,
    ) : FeedUIState
}

data class FeedTuitsState(
    val data: List<Tuit> = emptyList(),
    val replies: List<Tuit> = emptyList(),
    val tuit: Tuit = Tuit(0, "", "", "", false, 0, ""),
    val reply: Tuit = Tuit(0, "", "", "", false, 0, ""),
    val replyMessage: String = "",
)

@HiltViewModel
class TuitsViewModel
    @Inject
    constructor(
        private val tuitsRepo: TuitsRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<FeedUIState>(FeedUIState.Loading)
        val uiState = _uiState.asStateFlow()

        private val _feedTuitsState = MutableStateFlow(FeedTuitsState())
        val feedTuitsState = _feedTuitsState.asStateFlow()

        fun getAllTuits() =
            viewModelScope.launch {
                tuitsRepo
                    .getTuits()
                    .onSuccess { listOfTuits ->
                        _feedTuitsState.update { state ->
                            state.copy(data = listOfTuits)
                        }
                        _uiState.update { FeedUIState.Success }
                    }.onFailure { exception ->
                        _uiState.update { FeedUIState.Error(message = exception) }
                    }
            }

        fun getTuitById(id: Int) {
            Log.i("id_tuit_from_api", "$id")
            viewModelScope.launch {
                tuitsRepo
                    .getTuitByID(id)
                    .onSuccess { tuit ->
                        _feedTuitsState.update { state ->
                            state.copy(tuit = tuit)
                        }
                        _uiState.update {
                            FeedUIState.Success
                        }
                    }.onFailure { exception ->
                        _uiState.update { FeedUIState.Error(message = exception) }
                    }
            }
        }

        fun onReplyChanged(message: String) {
            _feedTuitsState.update {
                it.copy(replyMessage = message)
            }
        }

        fun getTuitReplies(tuitId: Int) {
            viewModelScope.launch {
                tuitsRepo
                    .getAllTuitReplies(tuitId)
                    .onSuccess { replies ->
                        _feedTuitsState.update {
                            // actualiza el estado del valor de replies
                            it.copy(replies = replies)
                        }
                        _uiState.update {
                            // actualiza el estado de la ui
                            FeedUIState.Success
                        } // (Lo hago unicamente cuando la request a la API es exitosa)
                    }.onFailure { exception ->
                        _uiState.update { FeedUIState.Error(message = exception) }
                    }
            }
        }

        fun addReply(
            tuit: Tuit,
            message: String,
        ) {
            var reply = Tuit(0, "", "", "", false, 0, "")
            viewModelScope.launch {
                tuitsRepo
                    .addTuitReply(key = tuit, message = message)
                    .onSuccess { tuit ->
                        reply = tuit
                        _feedTuitsState.update {
                            it.copy(reply = tuit)
                        }
                        _uiState.update {
                            FeedUIState.Success
                        }
                    }.onFailure { exception ->
                        _uiState.update { FeedUIState.Error(message = exception) }
                    }
            }
            updateTuitReplies(reply)
        }

        fun updateTuitReplies(tuit: Tuit) {
            val currentFeedState = feedTuitsState.value.replies
            val feedTuits = currentFeedState.toMutableList()
            feedTuits.add(tuit)
            _feedTuitsState.update { state ->
                state.copy(replies = feedTuits.toList())
            }
        }

        fun onFavoriteChange(tuit: Tuit) {
            if (tuit.liked) {
                removeLikes(tuit)
            } else {
                addLikes(tuit)
            }
        }

        fun removeLikes(tuit: Tuit) {
            viewModelScope.launch {
                tuitsRepo
                    .removeTuitLike(key = tuit)
                    .onSuccess {
                        Log.i("remove_like_onSuccess", "${it.liked}")
                        _uiState.update { FeedUIState.Success }
                    }.onFailure { it ->
                        Log.i("remove_like_onFailure", "$it")
                    }
            }
            updateTuit(tuit)
            Log.i("after_like_remove_state", "${tuit.liked}")
        }

        fun addLikes(tuit: Tuit) {
            viewModelScope.launch {
                tuitsRepo
                    .updateTuitLikes(key = tuit)
                    .onSuccess {
                        Log.i("add_likes_OnSuccess", "${it.likes}")
                        _uiState.update { FeedUIState.Success }
                    }.onFailure { it ->
                        Log.i("add_likes_OnFailure", "$it")
                    }
            }
            updateTuit(tuit)
            Log.i("after_like_add_state", "${tuit.liked}")
        }

        fun updateTuit(tuit: Tuit) {
            val currentFeedState = feedTuitsState.value.data
            val feedTuits = currentFeedState.toMutableList()
            val index = feedTuits.indexOfFirst { it.id == tuit.id }
            if (index != -1) {
                val tuitLiked = feedTuits[index]
                val newLikes = if (!tuit.liked) tuitLiked.likes + 1 else tuitLiked.likes - 1
                feedTuits[index] = tuitLiked.copy(liked = !tuit.liked, likes = newLikes)
            }
            _feedTuitsState.update { state ->
                state.copy(data = feedTuits.toList())
            }
        }
    }
