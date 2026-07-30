package com.example.di

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.network.BearingApiService
import com.example.network.MockBearingInterceptor
import com.example.repository.SearchRepository
import com.example.repository.SearchRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer {
    val searchRepository: SearchRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    companion object {
        private const val BASE_URL = "https://bearinghub.api.internal/"
    }

    private val database: AppDatabase by lazy {
        AppDatabase.getDatabase(context)
    }

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    private val okHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(MockBearingInterceptor())
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    private val apiService: BearingApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(BearingApiService::class.java)
    }

    override val searchRepository: SearchRepository by lazy {
        SearchRepositoryImpl(
            bearingDao = database.bearingDao(),
            apiService = apiService
        )
    }
}
