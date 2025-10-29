package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.RegisterRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.SendTuistRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.UpdateProfileRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.FeedResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.LikeTuitResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.LoginResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.ProfileResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.RegisterResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.SendTuitResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.TuitResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.UnLikeTuitResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.UpdateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TuiterApi {

    @POST("/api/v1/users")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("/api/v1/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/api/v1/me/profile")
    suspend fun getProfile(): Response<ProfileResponse>

    @PUT("/api/v1/me/profile")
    suspend fun updateProfile(@Body updateProfileRequest: UpdateProfileRequest): Response<UpdateResponse>

    @GET("/api/v1/me/feed?page=1")
    suspend fun getFeed(): Response<FeedResponse>

    @GET("/api/v1/me/tuits/:tuitID")
    suspend fun getTuitById(@Path("tuit_id") id: String): Response<TuitResponse>

    @POST("/api/v1/me/tuits")
    suspend fun sendTuit(@Body sendTuistRequest: SendTuistRequest): Response<SendTuitResponse>

    @POST("v1/me/tuits/{tuit_id}/likes")
    suspend fun likeTuit(@Path("tuit_id") id: String): Response<LikeTuitResponse>

    @DELETE("v1/me/tuits/{tuit_id}/likes")
    suspend fun unlikeTuit(@Path("tuit_id") id: String): Response<UnLikeTuitResponse>

}
