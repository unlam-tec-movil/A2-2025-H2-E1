package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuiterDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.AuthKey
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.RegisterRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserApiResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.api.TuiterApi
import retrofit2.Response
import javax.inject.Inject

class UserDefaultRepository
    @Inject
    constructor(
        private val tuiterApi: TuiterApi,
        private val tuiterDao: TuiterDao
    ) : UserRepository {
        override suspend fun saveUserToken(token: String) {
            tuiterDao.saveKey(
                AuthKey(
                    token = token,
                ),
            )
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
        ) {
            TODO("Not yet implemented")
        }

    suspend fun register(request: RegisterRequest): Response<UserApiResponse> {
        return tuiterApi.register(request)
    }

    override suspend fun getUserProfileData(): UserProfileDataApiResponse = tuiterApi.getUserProfileData()

    override suspend fun setUserProfileData(newProfileData: UserProfileDataApiRequest) = tuiterApi.updateUserProfileData(newProfileData)


    }
