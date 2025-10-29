package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.TuiterApi
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.RegisterRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.RegisterResponse
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    val api: TuiterApi
){

    suspend fun register(request: RegisterRequest): Response<RegisterResponse> {
        return api.register(request)
    }

    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return api.login(request)
    }

}