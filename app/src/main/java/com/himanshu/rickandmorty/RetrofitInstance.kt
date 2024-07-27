package com.himanshu.rickandmorty

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/* to create singleton instance of retrofit lazily  */

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: RickAndMortyApiService by lazy {
        retrofit.create(RickAndMortyApiService::class.java)
    }
}