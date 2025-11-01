package ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authKeys")
data class AuthKey(
    @PrimaryKey(autoGenerate = false)
    val token: String,
)