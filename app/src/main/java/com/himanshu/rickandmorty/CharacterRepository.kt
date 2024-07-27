package com.himanshu.rickandmorty

class CharacterRepository(private val apiService: RickAndMortyApiService) {

    suspend fun getCharacters( page: Int) =  apiService.getCharacters(page)

    suspend fun searchCharacters( name: String) =  apiService.searchCharacters(name)
}