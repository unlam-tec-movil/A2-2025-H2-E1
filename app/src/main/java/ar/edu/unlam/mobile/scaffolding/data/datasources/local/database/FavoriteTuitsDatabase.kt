package ar.edu.unlam.mobile.scaffolding.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuiterDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.TuitsBorrador
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserSavedEntity

@Database(
    entities = [AuthKey::class, TuitsBorrador::class, UserSavedEntity::class],
    version = 6,
    exportSchema = false,
)
abstract class FavoriteTuitsDatabase : RoomDatabase() {
    abstract fun tuitDao(): TuiterDao
}
