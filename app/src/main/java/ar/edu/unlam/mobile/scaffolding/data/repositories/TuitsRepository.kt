package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.TuitIDEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import kotlinx.coroutines.flow.Flow

interface TuitsRepository {
    suspend fun getTuits(): ApiOperation<List<Tuit>>

    suspend fun updateTuitLikes(key: Tuit): ApiOperation<Tuit>

    suspend fun removeTuitLike(key: Tuit): ApiOperation<Tuit>

    suspend fun addTuitReply(
        key: Tuit,
        message: String,
    ): ApiOperation<Tuit>

    suspend fun getAllTuitReplies(key: Int): ApiOperation<List<Tuit>>

    suspend fun getTuitByID(key: Int): ApiOperation<Tuit>

    suspend fun saveFavoriteTuit(tuit: Tuit)

    suspend fun deleteFavoriteSavedTuit(tuit: Tuit)

    fun getAllFavoriteTuitIDs(): Flow<List<TuitIDEntity>>

    suspend fun deleteAllFavoriteTuits()
}
