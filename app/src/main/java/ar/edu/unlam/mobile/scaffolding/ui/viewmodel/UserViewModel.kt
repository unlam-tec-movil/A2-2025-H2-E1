package ar.edu.unlam.mobile.scaffolding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.RegisterRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserApiResponse
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserDefaultRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserDefaultRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<UserApiResponse?>(null)
    val registerState = _registerState.asStateFlow()

    fun register(name: String, password: String, email: String) {

        viewModelScope.launch {

            val response = repository.register(RegisterRequest(name, password, email))

            if (response.isSuccessful) { // acá, verificamos que la respuesta de la api sea exitosa.
                val userResponse = response.body()
                _registerState.value = userResponse

                userResponse?.token?.let { token ->
                    repository.saveUserToken(token)
                }
            } else {
                val code = response.code()
                val message = response.message()
                val errorBody = response.errorBody()?.string()
                println("⚠️ Error en registro:")
                println("Código: $code")
                println("Mensaje: $message")
                println("Cuerpo del error: $errorBody")
            }

            //Se podria manejar con catch en caso de ocurrr algun exception.
        }
    }

}
