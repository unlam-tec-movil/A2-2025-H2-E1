package ar.edu.unlam.mobile.scaffolding.data.datasources.local.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class UserProfileDataApiResponse(
    val name: String,
    @SerializedName(value = "avatar_url")
    val avatarUrl: String,
    val email: String,
)
