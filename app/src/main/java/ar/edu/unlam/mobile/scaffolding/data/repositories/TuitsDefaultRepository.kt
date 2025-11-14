package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuiterDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Reply
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserApiResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.api.TuiterApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class TuitsDefaultRepository
    @Inject
    constructor(
        @Named("AuthApi") private val tuiterApi: TuiterApi,
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

        override suspend fun addTuitReply(
            key: Tuit,
            message: String,
        ): ApiOperation<Tuit> =
            safeApiRequest {
                tuiterApi.addTuitReply(
                    tuitId = key.id,
                    reply = Reply(message = message),
                )
            }

        override suspend fun getAllTuitReplies(key: Int): ApiOperation<List<Tuit>> =
            safeApiRequest {
                tuiterApi.getTuitReplies(tuitId = key)
            }

        override suspend fun getTuitByID(key: Int): ApiOperation<Tuit> =
            safeApiRequest {
                tuiterApi.getTuit(tuitId = key)
            }

        suspend fun logIn(
            email: String,
            password: String,
        ): UserApiResponse = tuiterApi.logIn(LoginRequest(email = email, password = password))

        override fun saveFavoriteTuit(key: Tuit) {
            TODO("Not yet implemented")
        }

        override fun deleteTuit(id: String) {
            TODO("Not yet implemented")
        }

        override fun getAllFavoriteTuits(): Flow<List<AuthKey>> = tuiterDao.getAllTuits()

        override fun deleteAllFavoriteTuits() {
            TODO("Not yet implemented")
        }

        suspend fun saveFavoriteTuit(key: AuthKey) {
            tuiterDao.saveKey(key)
        }

        suspend fun deleteTuit(key: AuthKey) {
            tuiterDao.deleteSavedTuit(key)
        }

        suspend fun deleteAllSavedTuits() {
            tuiterDao.deleteAllTokensSaved()
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
