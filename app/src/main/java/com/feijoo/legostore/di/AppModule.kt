package com.feijoo.legostore.di

import com.feijoo.legostore.api.ApiService
import com.feijoo.legostore.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /** Provide retrofit **/
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(Constants.TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(Constants.TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(Constants.TIMEOUT.toLong(), TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.CONNECTION_STRING)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    /** Provide api service **/
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)

    }

}