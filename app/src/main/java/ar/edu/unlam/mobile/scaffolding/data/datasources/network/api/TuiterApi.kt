package ar.edu.unlam.mobile.scaffolding.data.datasources.network.api

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.RegisterRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TuiterApi {
    @GET("/api/v1/me/feed")
    suspend fun getTuits(): List<Tuit>

    @POST(value = "/api/v1/login")
    suspend fun logIn(
        @Body request: LoginRequest,
    ): UserApiResponse

    @POST("/api/v1/users")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<UserApiResponse>
}
