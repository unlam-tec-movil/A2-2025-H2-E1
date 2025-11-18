package ar.edu.unlam.mobile.scaffolding.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.RegisterRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserApiResponse
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserDefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel
    @Inject
    constructor(
        private val repository: UserDefaultRepository,
    ) : ViewModel() {
        private val _registerState = MutableStateFlow<UserApiResponse?>(null)
        val registerState = _registerState.asStateFlow()
        private val _emailDuplicated = MutableStateFlow(false)
        val emailDuplicated = _emailDuplicated.asStateFlow()

        fun register(
            name: String,
            password: String,
            email: String,
        ) {
            viewModelScope.launch {
                val response = repository.register(RegisterRequest(name, password, email))

                if (response.isSuccessful) {
                    val userResponse = response.body()
                    _registerState.value = userResponse

                    userResponse?.token?.let { token ->
                        repository.saveUserToken(token)
                    }
                } else {
                    val code = response.code()
                    if (code == 500) {
                        _emailDuplicated.value = true
                    }
                }
            }
        }
    }
