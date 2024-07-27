package com.himanshu.rickandmorty

import com.himanshu.rickandmorty.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterResponse

    @GET("character")
    suspend fun searchCharacters(@Query("name") name: String): CharacterResponse
}