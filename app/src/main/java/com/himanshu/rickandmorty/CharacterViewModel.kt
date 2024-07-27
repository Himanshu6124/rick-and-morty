package com.himanshu.rickandmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.rickandmorty.model.CharacterResponse
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _characters  = MutableLiveData<CharacterResponse>()
    val characters  : LiveData<CharacterResponse> = _characters

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var currentPage = 1

    fun getCharacters(page :Int){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val res = repository.getCharacters(page)
            _isLoading.postValue(false)
            _characters.postValue(res)
        }
    }

    fun searchCharacters(name :String){
        viewModelScope.launch {
            val res = repository.searchCharacters(name)
            _characters.postValue(res)
        }
    }
}