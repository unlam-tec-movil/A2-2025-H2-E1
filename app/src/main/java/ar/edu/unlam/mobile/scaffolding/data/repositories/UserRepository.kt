package ar.edu.unlam.mobile.scaffolding.data.repositories

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

}
