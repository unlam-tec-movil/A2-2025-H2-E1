package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuiterDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.datastore.UserDataStore
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserSavedEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserApiResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.api.TuiterApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject
import javax.inject.Named

class UserDefaultRepository
    @Inject
    constructor(
        private val tuiterDao: TuiterDao,
        private val userDataStore: UserDataStore,
        @Named("PublicApi") private val publicApi: TuiterApi,
        @Named("AuthApi") private val authApi: TuiterApi,
    ) : UserRepository {
        override suspend fun saveUserToken(token: String): Flow<String> {
            userDataStore.saveUserToken(token)
            return userDataStore.getUserToken().filterNotNull()
        }

        override suspend fun createUser(
            name: String,
            email: String,
            password: String,
        ) {
            TODO("Not yet implemented")
        }

        override suspend fun loginUser(
            email: String,
            password: String,
        ): UserApiResponse = publicApi.logIn(LoginRequest(email = email, password = password))

        override fun getUserToken(): Flow<String?> = userDataStore.getUserToken()

        override suspend fun deleteUserToken() {
            userDataStore.deleteUserToken()
            tuiterDao.deleteAllTokensSaved()
        }

        override suspend fun deleteFavoriteUserSaved(userSavedEntity: UserSavedEntity) {
            tuiterDao.deleteSavedUserById(userSavedEntity = userSavedEntity)
        }

        override suspend fun getUserProfileData(): UserProfileDataApiResponse = authApi.getUserProfileData()

        override suspend fun getUserProfileDataById(tuit: Tuit): UserProfileDataApiResponse =
            publicApi.getUserProfileDataById(tuit.authorId)

        override suspend fun setUserProfileData(newProfileData: UserProfileDataApiRequest) = authApi.updateUserProfileData(newProfileData)

        override fun getAllSavedUsers(): Flow<List<UserSavedEntity>> = tuiterDao.getAllSavedUsers()

        override suspend fun saveFavoriteUser(favoriteUserIdEntity: UserSavedEntity) {
            tuiterDao.saveFavoriteUser(favoriteUserIdEntity)
        }

        override suspend fun getSavedUserById(userId: Int): UserSavedEntity? = tuiterDao.getSavedUserById(userId)
    }
