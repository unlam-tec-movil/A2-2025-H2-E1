package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserSavedEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserApiResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUserToken(token: String): Flow<String>

    fun getUserToken(): Flow<String?>

    suspend fun deleteUserToken()

    suspend fun createUser(
        name: String,
        email: String,
        password: String,
    )

    suspend fun loginUser(
        email: String,
        password: String,
    ): UserApiResponse

    suspend fun getUserProfileData(): UserProfileDataApiResponse

    suspend fun setUserProfileData(newProfileData: UserProfileDataApiRequest)

    suspend fun getUserProfileDataById(tuit: Tuit): UserProfileDataApiResponse

    fun getAllSavedUsers(): Flow<List<UserSavedEntity>>

    suspend fun saveFavoriteUser(favoriteUserIdEntity: UserSavedEntity)

    suspend fun getSavedUserById(userId: Int): UserSavedEntity?

    suspend fun deleteFavoriteUserSaved(userSavedEntity: UserSavedEntity)
}
