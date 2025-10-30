package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit

interface TuitsRepository {
    suspend fun getTuits(): List<Tuit>

    fun saveFavoriteTuit(key: Tuit)

    fun deleteTuit(id: String)

    fun getAllFavoriteTuits(): List<Tuit>

    fun deleteAllFavoriteTuits()
}
