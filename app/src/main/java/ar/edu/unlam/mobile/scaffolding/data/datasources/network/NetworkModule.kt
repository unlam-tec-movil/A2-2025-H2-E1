package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTuiterApi(): TuiterApi {
        return RetrofitInstance.getAPI()
    }

}
