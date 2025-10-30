package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuiterDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserApiResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.api.TuiterApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TuitsDefaultRepository
    @Inject
    constructor(
        private val tuiterApi: TuiterApi,
        private val tuiterDao: TuiterDao,
    ) : TuitsRepository {
        override suspend fun getTuits(): List<Tuit> = tuiterApi.getTuits()

        override fun saveFavoriteTuit(key: Tuit) {
            TODO("Not yet implemented")
        }

        override fun deleteTuit(id: String) {
            TODO("Not yet implemented")
        }

        override fun getAllFavoriteTuits(): List<Tuit> {
            TODO("Not yet implemented")
        }

        override fun deleteAllFavoriteTuits() {
            TODO("Not yet implemented")
        }

        suspend fun logIn(
            email: String,
            password: String,
        ): UserApiResponse = tuiterApi.logIn(LoginRequest(email = email, password = password))

        suspend fun saveFavoriteTuit(key: AuthKey) {
            tuiterDao.saveKey(key)
        }

        suspend fun deleteTuit(key: AuthKey) {
            tuiterDao.deleteSavedTuit(key)
        }

        suspend fun getAllSavedTuits(): Flow<List<AuthKey>> = tuiterDao.getAllTuits()

        suspend fun deleteAllSavedTuits() {
            tuiterDao.deleteAllTuitsSaved()
        }
    }
