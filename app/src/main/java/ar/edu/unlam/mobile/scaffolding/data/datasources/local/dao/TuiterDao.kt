package ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import kotlinx.coroutines.flow.Flow

@Dao
interface TuiterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveKey(key: AuthKey)

    @Query(value = "SELECT * FROM authKeys ORDER BY token DESC")
    fun getAllTuits(): Flow<List<AuthKey>>

    @Delete
    fun deleteSavedTuit(key: AuthKey)

    @Query(value = "DELETE FROM authKeys")
    suspend fun deleteAllTuitsSaved()

}