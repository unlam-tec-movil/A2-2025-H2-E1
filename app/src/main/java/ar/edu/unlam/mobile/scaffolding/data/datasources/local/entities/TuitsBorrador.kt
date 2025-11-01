package ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "borrador")
class TuitsBorrador(
    @PrimaryKey(autoGenerate = false)
    val token: String,
    val borrador: String,
)
