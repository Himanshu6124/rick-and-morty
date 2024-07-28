package com.himanshu.rickandmorty.repository

import com.himanshu.rickandmorty.network.RickAndMortyApiService

class CharacterRepository(private val apiService: RickAndMortyApiService) {

    suspend fun getCharacters( page: Int) =  apiService.getCharacters(page)

    suspend fun searchCharacters( name: String) =  apiService.searchCharacters(name)
}