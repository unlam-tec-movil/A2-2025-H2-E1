package ar.edu.unlam.mobile.scaffolding.data.datasources.network.response

data class RegisterResponse(
    val name: String,
    val email: String,
    val token: String
)

data class LoginResponse(
    val name: String,
    val email: String,
    val token: String
)
