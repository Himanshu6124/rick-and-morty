package com.himanshu.rickandmorty

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


/* to create singleton instance of retrofit lazily  */

object RetrofitInstance {
    val TAG = "RetrofitInstance"

    fun provideCache(context: Context): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        Log.i(TAG,context.cacheDir.path)
        return Cache(File(context.cacheDir, "http-cache"), cacheSize)
    }

    fun provideOkHttpClient(context: Context, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context) == true)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 100).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                Log.i(TAG,"provide Http client $request")
                chain.proceed(request)
            }
            .build()
    }

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideApiService(retrofit: Retrofit) :RickAndMortyApiService {
        val apiService: RickAndMortyApiService by lazy {
            retrofit.create(RickAndMortyApiService::class.java)
        }
        return apiService
    }
}