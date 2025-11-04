package ar.edu.unlam.mobile.scaffolding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginResponse?>(null)
    val loginState = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<RegisterResponse?>(null)
    val registerState = _registerState.asStateFlow()

    fun login(email: String, password: String) {

        viewModelScope.launch {

            val response = repository.login(LoginRequest(email, password))
            if (response.isSuccessful) { // acá, verificamos que la respuesta de la api sea exitosa.
                _loginState.value = response.body()
            } else {
                _loginState.value = null
                println("Error en login: ${response.code()} ${response.message()}")
            }

            //Se podria manejar con catch en caso de ocurrr algun exception.

        }
    }

    fun register(name: String, password: String, email: String) {

        viewModelScope.launch {

            val response = repository.register(RegisterRequest(name,password, email))

            if (response.isSuccessful) { // acá, verificamos que la respuesta de la api sea exitosa.
                _registerState.value = response.body()
            } else {
                _registerState.value = null
                println("Error en login: ${response.code()} ${response.message()}")
            }

            //Se podria manejar con catch en caso de ocurrr algun exception.
        }
    }

}
