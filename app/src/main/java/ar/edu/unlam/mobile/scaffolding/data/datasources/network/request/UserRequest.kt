package ar.edu.unlam.mobile.scaffolding.data.datasources.network.request

data class RegisterRequest(
    val name: String,
    val password: String,
    val email: String
)

data class LoginRequest(
    val email: String,
    val password: String
)