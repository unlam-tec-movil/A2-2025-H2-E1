package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.RegisterRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.RegisterResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface TuiterApi {

    @POST("/api/v1/users")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("/api/v1/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

}
