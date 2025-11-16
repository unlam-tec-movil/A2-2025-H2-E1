package ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteTuits")
data class TuitIDEntity(
    @PrimaryKey(autoGenerate = false)
    val tuitId: Int,
)
