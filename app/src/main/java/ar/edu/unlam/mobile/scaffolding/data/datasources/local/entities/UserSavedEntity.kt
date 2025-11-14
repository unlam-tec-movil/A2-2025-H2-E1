package ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities

import androidx.room.Entity

@Entity(tableName = "favorite_users", primaryKeys = ["authorId", "userFanEmail"])
data class UserSavedEntity(
    val authorId: Int,
    val avatarUrl: String,
    val userFanEmail: String,
    val author: String,
)
