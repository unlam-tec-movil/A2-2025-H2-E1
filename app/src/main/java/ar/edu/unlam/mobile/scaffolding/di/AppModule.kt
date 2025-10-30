package ar.edu.unlam.mobile.scaffolding.di

import android.app.Application
import androidx.room.Room
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuiterDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.database.FavoriteTuitsDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.api.TuiterApi
import ar.edu.unlam.mobile.scaffolding.utils.Constants.APPLICATION_TOKEN
import ar.edu.unlam.mobile.scaffolding.utils.Constants.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun favoriteTuitsDatabaseProvider(contex: Application): FavoriteTuitsDatabase =
        Room
            .databaseBuilder(
                context = contex,
                klass = FavoriteTuitsDatabase::class.java,
                name = "favoriteTuits_DB",
            ).build()

    @Provides
    @Singleton
    fun tuitDaoProvider(db: FavoriteTuitsDatabase): TuiterDao = db.tuitDao()

    @Provides
    @Singleton
    fun provideApi(): TuiterApi {
        val userToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFydHVyb2phczMyQGdtYWlsLmNvbSIsImV4cCI6MTc2NDEwODAzNywiaXNzIjoidW5sYW0tdHVpdGVyIiwibmFtZSI6ImFydHVyb2phczMyQGdtYWlsLmNvbSIsInN1YiI6MjgyfQ.zQsar7wJ0-qbICT7qRrVRCVa4PdPcvr3kE1NizmZKRA"

        val okHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor { chain ->
                    val originalRequest: Request = chain.request()
                    val newRequest: Request =
                        originalRequest
                            .newBuilder()
                            .header(name = "Authorization", value = userToken)
                            .header(name = "Application-Token", value = APPLICATION_TOKEN)
                            .build()
                    chain.proceed(newRequest)
                }.build()
        val retrofit =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(Gson()))

        return retrofit.build().create(TuiterApi::class.java)
    }
}
