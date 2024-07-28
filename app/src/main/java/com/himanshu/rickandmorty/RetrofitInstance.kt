package com.himanshu.rickandmorty

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/* create retrofit instance for api call */

object RetrofitInstance {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    private const val CACHE_SIZE = (10 * 1024 * 1024).toLong() // 10 MB
    private const val MAX_AGE = 300 // 5 minutes
    private const val MAX_STALE = 60 * 60 * 24 * 7 // 1 week

    private lateinit var apiService: RickAndMortyApiService

    private fun provideCache(context: Context): Cache {
        return Cache(File(context.cacheDir, "http-cache"), CACHE_SIZE)
    }

    private fun provideOkHttpClient(context: Context, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context))
                    request.newBuilder().header("Cache-Control", "public, max-age=$MAX_AGE").build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=$MAX_STALE").build()
                chain.proceed(request)
            }
            .build()
    }

    private fun hasNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideApiService(context: Context): RickAndMortyApiService {
        if (!::apiService.isInitialized) {
            val cache = provideCache(context)
            val okHttpClient = provideOkHttpClient(context, cache)
            val retrofit = provideRetrofit(okHttpClient)
            apiService = retrofit.create(RickAndMortyApiService::class.java)
        }
        return apiService
    }
}