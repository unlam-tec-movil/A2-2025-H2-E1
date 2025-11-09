package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserApiResponse
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
}
