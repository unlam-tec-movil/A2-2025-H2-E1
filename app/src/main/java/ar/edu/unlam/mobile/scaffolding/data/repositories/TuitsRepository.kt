package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuiterDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserApiResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.api.TuiterApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TuitsRepository @Inject constructor(
    private val tuiterApi: TuiterApi,
    private val tuiterDao: TuiterDao
) {
    suspend fun getTuits(): List<Tuit> {
        return tuiterApi.getTuits()
    }

    suspend fun logIn(email: String, password: String): UserApiResponse {
        return tuiterApi.logIn(LoginRequest(email = email, password = password))
    }

    suspend fun saveFavoriteTuit(key: AuthKey) {
        tuiterDao.saveKey(key)
    }

    suspend fun deleteTuit(key: AuthKey) {
        tuiterDao.deleteSavedTuit(key)
    }

    fun getAllSavedTuits(): Flow<List<AuthKey>> {
        return tuiterDao.getAllTuits()
    }

    suspend fun deleteAllSavedTuits() {
        tuiterDao.deleteAllTuitsSaved()
    }

}