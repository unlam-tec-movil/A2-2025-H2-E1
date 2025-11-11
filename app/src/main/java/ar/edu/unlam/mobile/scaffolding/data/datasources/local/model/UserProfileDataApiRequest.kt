package ar.edu.unlam.mobile.scaffolding.data.datasources.local.model

import com.google.gson.annotations.SerializedName

data class UserProfileDataApiRequest(
    val name: String,
    @SerializedName(value = "avatar_url")
    val avatarUrl: String,
)
