package com.himanshu.rickandmorty

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.rickandmorty.model.CharacterResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {
    val TAG = "CharacterViewModel"
    private val _characters  = MutableLiveData<CharacterResponse>()
    val characters  : LiveData<CharacterResponse> = _characters

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    private var searchJob: Job? = null

    var currentPage = 1

    init {
        getCharacters(currentPage)
    }

    fun getCharacters(page :Int){
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                val res = repository.getCharacters(page)
                _isLoading.postValue(false)
                _characters.postValue(res)
            }
            catch (ex : Exception){
                Log.e(TAG,"exception occurred in get characters")
                _isLoading.postValue(false)
                _errorMessage.value = ex.message
            }
        }
    }

    fun searchCharacters(name :String){
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            try {
                _isLoading.postValue(true)
                val res = repository.searchCharacters(name)
                _isLoading.postValue(false)
                _characters.postValue(res)
            }
            catch (ex : Exception){
                Log.e(TAG,"exception occurred in search characters")
                _isLoading.postValue(false)
                _errorMessage.value = ex.message
            }
        }
    }


//    private fun onError(errorMessage: String?) {
//
//        val message = if (errorMessage.isNullOrBlank() or errorMessage.isNullOrEmpty()) "Unknown Error"
//        else errorMessage
//
//        errorMessage = StringBuilder("ERROR: ")
//            .append("$message some data may not displayed properly").toString()
//
//        _isError.value = true
//        _isLoading.value = false
//    }
}