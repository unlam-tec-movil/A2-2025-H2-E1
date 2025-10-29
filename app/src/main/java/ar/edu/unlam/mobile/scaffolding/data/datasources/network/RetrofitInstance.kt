package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://tuiter.fragua.com.ar"
    private const val APPLICATION_TOKEN = ""
    private var USER_TOKEN: String? = null

    fun setUserToken(token: String) {
        USER_TOKEN = token
    }

    fun getAPI(): TuiterApi {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Application-Token", APPLICATION_TOKEN)
                    .apply {
                        USER_TOKEN?.let {
                            addHeader("Authorization", it)
                        }
                    }
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TuiterApi::class.java)
    }
}
