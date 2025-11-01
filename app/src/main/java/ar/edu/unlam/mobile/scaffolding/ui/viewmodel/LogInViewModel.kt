package ar.edu.unlam.mobile.scaffolding.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey
import ar.edu.unlam.mobile.scaffolding.data.repositories.TuitsDefaultRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
sealed interface LoginState {
    data class Success(
        val message: String,
    ) : LoginState

    data object Loading : LoginState

    data object Idle : LoginState

    data class Error(
        val message: String,
    ) : LoginState
}

@HiltViewModel
class LogInViewModel
    @Inject
    constructor(
        private val tuitsRepo: TuitsDefaultRepository,
        private val userRepo: UserRepository,
    ) : ViewModel() {
        private val _savedTuits = MutableStateFlow<List<AuthKey>>(emptyList())
        val savedTuits: StateFlow<List<AuthKey>> = _savedTuits

        private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
        val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

        init {
            viewModelScope.launch {

                tuitsRepo.getAllFavoriteTuits().collect { listaDeKeys ->
                    _savedTuits.value = listaDeKeys
                }
            }
        }

        fun logInVM(
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                _loginState.value = LoginState.Loading
                try {
                    val loginResponse = tuitsRepo.logIn(email = email, password = password)
                    userRepo.saveUserToken(loginResponse.token)
                    _loginState.value = LoginState.Success("Success")
                } catch (e: Exception) {
                    _loginState.value = LoginState.Error("${e.message} : Check your credentials")
                }
            }
        }

        fun resetState() {
            _loginState.value = LoginState.Idle
        }
    }
