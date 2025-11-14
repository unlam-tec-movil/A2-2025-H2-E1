package ar.edu.unlam.mobile.scaffolding.data.datasources.local.model

import com.google.gson.annotations.SerializedName

data class Tuit(
    val id: Int,
    val author: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val message: String,
    val liked: Boolean,
    val likes: Long,
    @SerializedName("author_id")
    val authorId: Int,
)
