package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiResponse

interface UserRepository {
    suspend fun saveUserToken(token: String)

    suspend fun createUser(
        name: String,
        email: String,
        password: String,
    )

    suspend fun loginUser(
        email: String,
        password: String,
    )

    suspend fun getUserProfileData(): UserProfileDataApiResponse

    suspend fun setUserProfileData(newProfileData: UserProfileDataApiRequest)
}
