package ar.edu.unlam.mobile.scaffolding.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiResponse
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UpdateProfileDataState {
    data class Success(
        val message: String,
    ) : UpdateProfileDataState

    data object Loading : UpdateProfileDataState

    data object Idle : UpdateProfileDataState

    data class Error(
        val message: String,
    ) : UpdateProfileDataState
}

@HiltViewModel
class EditUserViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _userProfileDataState = MutableStateFlow<UserProfileDataApiResponse?>(null)
        val userProfileDataState: StateFlow<UserProfileDataApiResponse?> =
            _userProfileDataState.asStateFlow()
        private val _userProfileDataUpdateState =
            MutableStateFlow<UpdateProfileDataState>(value = UpdateProfileDataState.Idle)
        val userProfileDataUpdateState: StateFlow<UpdateProfileDataState> = _userProfileDataUpdateState

        init {

            viewModelScope.launch {
                _userProfileDataState.value = userRepository.getUserProfileData()
            }
        }

        fun updateProfileData(profileDataUpdated: UserProfileDataApiRequest) {
            viewModelScope.launch {
                _userProfileDataUpdateState.value = UpdateProfileDataState.Loading
                try {
                    userRepository.setUserProfileData(profileDataUpdated)
                    _userProfileDataUpdateState.value = UpdateProfileDataState.Success("done")
                } catch (e: Exception) {
                    _userProfileDataUpdateState.value = UpdateProfileDataState.Error("error")
                }
            }
        }

        fun resetUpdateProfileDataState() {
            _userProfileDataUpdateState.value = UpdateProfileDataState.Idle
        }
    }
