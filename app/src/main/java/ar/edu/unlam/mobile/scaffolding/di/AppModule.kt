package ar.edu.unlam.mobile.scaffolding.di

import android.content.Context
import androidx.room.Room
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuiterDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.database.FavoriteTuitsDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.datastore.UserDataStore
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.api.TuiterApi
import ar.edu.unlam.mobile.scaffolding.utils.Constants.APPLICATION_TOKEN
import ar.edu.unlam.mobile.scaffolding.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun favoriteTuitsDatabaseProvider(
        @ApplicationContext context: Context,
    ): FavoriteTuitsDatabase =
        Room
            .databaseBuilder(context, FavoriteTuitsDatabase::class.java, "favoriteTuits_DB")
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    @Singleton
    fun tuitDaoProvider(db: FavoriteTuitsDatabase): TuiterDao = db.tuitDao()

    // 1. Creamos un Interceptor de Logging genérico
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    // 2. Creamos un OkHttpClient PÚBLICO (para Login/Register)
    @Provides
    @Singleton
    @Named("PublicOkHttpClient") // <-- Anotación para diferenciarlo
    fun providePublicOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder =
                    original
                        .newBuilder()
                        .header("Application-Token", APPLICATION_TOKEN)
                chain.proceed(builder.build())
            }.addInterceptor(loggingInterceptor)
            .build()

    // 3. Creamos un OkHttpClient PRIVADO (para peticiones autenticadas)
    @Provides
    @Singleton
    @Named("AuthOkHttpClient") // <-- Anotación para diferenciarlo
    fun provideAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        userDataStore: UserDataStore,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder =
                    original
                        .newBuilder()
                        .header("Application-Token", APPLICATION_TOKEN)

                // Leemos el token de forma segura
                val token = runBlocking { userDataStore.getUserToken().firstOrNull() }

                if (!token.isNullOrBlank()) {
                    builder.header("Authorization", token.trim())
                }
                chain.proceed(builder.build())
            }.addInterceptor(loggingInterceptor)
            .build()

    // 4. Creamos dos instancias de Retrofit, una para cada cliente
    @Provides
    @Singleton
    @Named("PublicApi")
    fun providePublicApi(
        @Named("PublicOkHttpClient") okHttpClient: OkHttpClient,
    ): TuiterApi =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TuiterApi::class.java)

    @Provides
    @Singleton
    @Named("AuthApi")
    fun provideAuthApi(
        @Named("AuthOkHttpClient") okHttpClient: OkHttpClient,
    ): TuiterApi =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TuiterApi::class.java)

    // DATASTORE
    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context,
    ): UserDataStore = UserDataStore(context)
}
