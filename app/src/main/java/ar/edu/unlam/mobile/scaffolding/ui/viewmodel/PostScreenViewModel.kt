package ar.edu.unlam.mobile.scaffolding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.TuitsBorrador
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostScreenViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        private val _postState = MutableStateFlow("")
        val postState: StateFlow<String> = _postState

        private val _uiState = MutableStateFlow<FeedState>(FeedState.Idle)
        val uiState: StateFlow<FeedState> = _uiState

        val borradorState: StateFlow<List<TuitsBorrador>> =
            postRepository
                .devolverBorradores()
                .stateIn(
                    scope = viewModelScope, // El ciclo de vida del ViewModel
                    started = SharingStarted.WhileSubscribed(5000), // Inicia cuando la UI observa y para 5s después
                    initialValue = emptyList(), // Valor inicial mientras se carga el primero
                )

        fun loadFeed() =
            viewModelScope.launch {
                _uiState.value = FeedState.Loading
                try {
                    val tuits = postRepository.getFeed()
                    _uiState.value = FeedState.Success(tuits)
                } catch (e: retrofit2.HttpException) {
                    if (e.code() == 401) {
                        _uiState.value = FeedState.Error("Sesión inválida/expirada (401). Iniciá sesión de nuevo.")
                    } else {
                        _uiState.value = FeedState.Error("Error HTTP ${e.code()}")
                    }
                } catch (e: java.io.IOException) {
                    _uiState.value = FeedState.Error("Sin conexión. Intentá nuevamente.")
                } catch (e: Exception) {
                    _uiState.value = FeedState.Error("Ocurrió un error inesperado.")
                }
            }

        fun textoATuitear(message: String): Job {
            val job =
                viewModelScope.launch {
                    try {
                        postRepository.postTuit(message)
                        println("Publicación de 'textoATuitear' exitosa.")
                        var borradorAEliminar = borradorState.value.find { it.textoBorrador == message }
                        if (borradorAEliminar != null) {
                            // postRepository.deleteBorradorPR(borradorAEliminar)
                            postRepository.deleteBorradorPR(borradorAEliminar)
                        }
                    } catch (e: Exception) {
                        println("Error en textoATuitear: ${e.message}")
                    }
                }
            return job
        }

        fun textoAGuardar(message: String) {
            viewModelScope.launch {
                if (message.isEmpty()) return@launch
                try {
                    postRepository.guardarBorrador(message)
                    println("Se guardó el borrador.")
                } catch (e: Exception) {
                    println("Error al guardar: ${e.message}")
                }
            }
        }

        fun onTextChanged(newText: String): String {
            _postState.value = newText
            return _postState.value
        }

        fun limpiarCampoDeTexto() {
            _postState.value = ""
        }

        sealed interface FeedState {
            data object Idle : FeedState

            data object Loading : FeedState

            data class Success(
                val tuits: List<Tuit>,
            ) : FeedState

            data class Error(
                val message: String,
            ) : FeedState
        }
    }
