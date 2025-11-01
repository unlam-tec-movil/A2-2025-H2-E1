package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import kotlinx.coroutines.flow.Flow

interface TuitsRepository {
    suspend fun getTuits(): List<Tuit>

    fun saveFavoriteTuit(key: Tuit)

    fun deleteTuit(id: String)

    fun getAllFavoriteTuits(): Flow<List<AuthKey>>

    fun deleteAllFavoriteTuits()
}
