package ar.edu.unlam.mobile.scaffolding.data.datasources.local.model

import com.google.gson.annotations.SerializedName

data class UserApiResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("token")
    val token: String,
)
