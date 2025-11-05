package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit

interface TuitsRepository {
    suspend fun getTuits(): ApiOperation<List<Tuit>>

    suspend fun updateTuitLikes(key: Tuit): ApiOperation<Tuit>

    suspend fun removeTuitLike(key: Tuit): ApiOperation<Tuit>

    fun saveFavoriteTuit(key: Tuit)

    fun deleteTuit(id: String)

    fun getAllFavoriteTuits(): List<Tuit>

    fun deleteAllFavoriteTuits()
}
