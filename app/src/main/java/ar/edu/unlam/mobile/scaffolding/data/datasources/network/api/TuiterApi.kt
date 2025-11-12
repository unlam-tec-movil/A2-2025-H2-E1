package ar.edu.unlam.mobile.scaffolding.data.datasources.network.api

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.RegisterRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Reply
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.TuitBody
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserApiResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TuiterApi {
    @GET("/api/v1/me/feed")
    suspend fun getTuits(): List<Tuit>

    @POST(value = "/api/v1/login")
    suspend fun logIn(
        @Body request: LoginRequest,
    ): UserApiResponse

    @POST(value = "/api/v1/me/tuits/{tuit_id}/likes")
    suspend fun updateTuitLikes(
        @Path(value = "tuit_id") tuitId: Int,
    ): Tuit

    @DELETE(value = "api/v1/me/tuits/{tuit_id}/likes")
    suspend fun removeTuitLike(
        @Path(value = "tuit_id") tuitId: Int,
    ): Tuit

    @POST("/api/v1/me/tuits")
    suspend fun postTuit(
        @Body tuit: TuitBody,
    )

    @GET(value = "/api/v1/me/tuits/{tuit_id}/replies")
    suspend fun getTuitReplies(
        @Path(value = "tuit_id") tuitId: Int,
    ): List<Tuit>

    @POST(value = "/api/v1/me/tuits/{tuit_id}/replies")
    suspend fun addTuitReply(
        @Path(value = "tuit_id") tuitId: Int,
        @Body reply: Reply,
    ): Tuit

    @GET(value = "/api/v1/me/tuits/{tuit_id}")
    suspend fun getTuit(
        @Path(value = "tuit_id") tuitId: Int,
    ): Tuit

    @POST("/api/v1/users")
    suspend fun register(
        @Body registerRequest: RegisterRequest,
    ): Response<UserApiResponse>

    @GET("/api/v1/me/profile")
    suspend fun getUserProfileData(): UserProfileDataApiResponse

    @PUT(value = "/api/v1/me/profile")
    suspend fun updateUserProfileData(
        @Body profileDataUpdated: UserProfileDataApiRequest,
    )
}
