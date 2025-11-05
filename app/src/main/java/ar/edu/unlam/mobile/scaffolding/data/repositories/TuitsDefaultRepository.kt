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
        override suspend fun getTuits(): ApiOperation<List<Tuit>> =
            safeApiRequest {
                tuiterApi.getTuits()
            }

        override suspend fun updateTuitLikes(key: Tuit): ApiOperation<Tuit> =
            safeApiRequest {
                tuiterApi.updateTuitLikes(
                    tuitId = key.id,
                )
            }

        override suspend fun removeTuitLike(key: Tuit): ApiOperation<Tuit> =
            safeApiRequest {
                tuiterApi.removeTuitLike(
                    tuitId = key.id,
                )
            }

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

        // cada llamada a una api externa pasa por un try/catch. En la clase ApiOperation se definen
        // dos posibles estados a esa llamada --> success o failure
        private inline fun <T> safeApiRequest(apiCall: () -> T): ApiOperation<T> =
            try {
                ApiOperation.Success(data = apiCall())
            } catch (e: Exception) {
                ApiOperation.Failure(exception = e)
            }
    }

sealed interface ApiOperation<T> {
    data class Success<T>(
        val data: T,
    ) : ApiOperation<T>

    data class Failure<T>(
        val exception: Exception,
    ) : ApiOperation<T>

    fun onSuccess(block: (T) -> Unit): ApiOperation<T> {
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (Exception) -> Unit): ApiOperation<T> {
        if (this is Failure) block(exception)
        return this
    }
}
