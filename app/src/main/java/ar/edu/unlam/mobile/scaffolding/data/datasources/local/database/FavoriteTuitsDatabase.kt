package ar.edu.unlam.mobile.scaffolding.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuiterDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey

@Database(
    entities = [AuthKey::class],
    version = 1,
    exportSchema = false,
)
abstract class FavoriteTuitsDatabase : RoomDatabase() {
    abstract fun tuitDao(): TuiterDao
}
