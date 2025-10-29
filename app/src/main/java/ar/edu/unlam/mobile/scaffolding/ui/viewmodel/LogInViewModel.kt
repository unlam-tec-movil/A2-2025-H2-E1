package ar.edu.unlam.mobile.scaffolding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey
import ar.edu.unlam.mobile.scaffolding.data.repositories.TuitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LoginUIState {
    object Loading : LoginUIState

    object Success : LoginUIState

    data class Error(
        val message: String,
    ) : LoginUIState
}

@HiltViewModel
class LogInViewModel
    @Inject
    constructor(
        private val tuitsRepo: TuitsRepository,
    ) : ViewModel() {
        private val _savedTuits = MutableStateFlow<List<AuthKey>>(emptyList())
        val savedTuits: StateFlow<List<AuthKey>> = _savedTuits

        init {
            viewModelScope.launch {

                tuitsRepo.getAllSavedTuits().collect { listaDeKeys ->
                    _savedTuits.value = listaDeKeys
                }
            }
        }

        fun logInVM(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                val loginResponse = tuitsRepo.logIn(email = email, password = password)
                val token = AuthKey(loginResponse.token)
                tuitsRepo.saveFavoriteTuit(token)
            }
        }
    }
