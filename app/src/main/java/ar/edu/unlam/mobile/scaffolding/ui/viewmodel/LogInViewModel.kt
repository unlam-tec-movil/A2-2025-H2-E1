package ar.edu.unlam.mobile.scaffolding.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.datastore.UserDataStore
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
    val userRepo: UserRepository,
    val userDataStore: UserDataStore,
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun logInVM(
        email: String,
        password: String,
        remember: Boolean = false,
    ) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                // backend login
                val loginResponse = userRepo.loginUser(email = email, password = password)
                userRepo.saveUserToken(loginResponse.token).first()

                if (remember) {
                    userDataStore.setRememberedUser(email)
                } else {
                    userDataStore.clearRememberedUser()
                }
                _loginState.value = LoginState.Success("Success")
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("${e.message} : Check your credentials")
                Log.e("LOGIN_VM_ERROR", "Error en LoginVM: ${e.message}")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}
