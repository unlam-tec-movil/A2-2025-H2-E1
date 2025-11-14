package ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.TuitsBorrador
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserSavedEntity
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
    suspend fun deleteAllTokensSaved()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBorrador(borrador: TuitsBorrador)

    @Query(value = "SELECT * FROM borrador")
    fun getBorrador(): Flow<List<TuitsBorrador>>

    @Query(value = "SELECT * FROM borrador WHERE id = :id")
    fun getBorradorId(id: Int): TuitsBorrador

    @Query(value = "SELECT * FROM borrador WHERE textoBorrador = :texto")
    fun getBorradorString(texto: String): TuitsBorrador

    @Delete
    suspend fun deleteBorrador(borrador: TuitsBorrador)

    // lectura síncrona para el interceptor
    @Query("SELECT token FROM authKeys ORDER BY rowid DESC LIMIT 1")
    fun getLatestTokenSync(): String?

    // guardar lista de ususarios favoritos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteUser(favoriteUserIdEntity: UserSavedEntity)

    @Query(value = "SELECT * FROM favorite_users ORDER BY authorId DESC")
    fun getAllSavedUsers(): Flow<List<UserSavedEntity>>

    @Query(value = "SELECT * FROM favorite_users WHERE authorId = :userId")
    suspend fun getSavedUserById(userId: Int): UserSavedEntity?

    @Delete
    suspend fun deleteSavedUserById(userSavedEntity: UserSavedEntity)
}
