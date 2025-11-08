package ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.TuitsBorrador
import kotlinx.coroutines.flow.Flow

@Dao
interface TuiterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveKey(key: AuthKey)

    @Query(value = "SELECT * FROM authKeys ORDER BY token DESC")
    fun getAllTuits(): Flow<List<AuthKey>>

    @Delete
    suspend fun deleteSavedTuit(key: AuthKey)

    @Query(value = "DELETE FROM authKeys")
    suspend fun deleteAllTuitsSaved()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBorrador(borrador: TuitsBorrador)

    @Query(value = "SELECT * FROM borrador")
    fun getBorrador(): Flow<List<TuitsBorrador>>

    @Query(value = "SELECT * FROM borrador WHERE id = :id")
    fun getBorradorId(id: Int): TuitsBorrador

    @Delete
    fun deleteBorrador(borrador: TuitsBorrador)
}
