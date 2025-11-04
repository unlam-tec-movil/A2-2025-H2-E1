package ar.edu.unlam.mobile.scaffolding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.TuitsBorrador
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

        private var _borradorState: Flow<List<TuitsBorrador>> =
            MutableStateFlow<List<TuitsBorrador>>(emptyList())
        val borradorState: StateFlow<List<TuitsBorrador>> =
            postRepository
                .devolverBorradores()
                .stateIn(
                    scope = viewModelScope, // El ciclo de vida del ViewModel
                    started = SharingStarted.WhileSubscribed(5000), // Inicia cuando la UI observa y para 5s después
                    initialValue = emptyList(), // Valor inicial mientras se carga el primero
                )

        init {
            viewModelScope.launch {
                try {
                    _postState.value = postRepository.postTuit(message = _postState.value).toString()
                    _borradorState = postRepository.devolverBorradores()
                } catch (e: Exception) {
                    // Importante: Capturar cualquier excepción para que el ViewModel
                    // no cause el crasheo fatal si la red falla.
                    println("Error al inicializar el PostScreenViewModel: ${e.message}")
                }
            }
        }

        fun textoATuitear(message: String) {
            viewModelScope.launch {
                try {
                    postRepository.postTuit(message)
                    println("Publicación de 'textoATuitear' exitosa.")
                } catch (e: Exception) {
                    println("Error en textoATuitear: ${e.message}")
                }
            }
        }

        fun textoAGuardar(message: String) {
            viewModelScope.launch {
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
    }
