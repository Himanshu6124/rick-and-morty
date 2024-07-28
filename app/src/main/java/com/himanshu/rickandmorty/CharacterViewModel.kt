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
    private val tag = "CharacterViewModel"

    private val _characters = MutableLiveData<CharacterResponse>()
    val characters: LiveData<CharacterResponse> = _characters

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private var searchJob: Job? = null

    private val _currentPage = MutableLiveData(1)
    val currentPage: LiveData<Int> = _currentPage

    init {
        getCharacters(_currentPage.value ?: 1)
    }

    private fun getCharacters(page: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null // Clear previous errors
                val res = repository.getCharacters(page)
                _characters.postValue(res)
            } catch (ex: Exception) {
                Log.e(tag, "exception occurred in get characters", ex)
                _errorMessage.value = ex.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchCharacters(name: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            try {
                _isLoading.value = true
                _errorMessage.value = null // Clear previous errors
                val res = repository.searchCharacters(name)
                _characters.postValue(res)
            } catch (ex: Exception) {
                Log.e(tag, "exception occurred in search characters", ex)
                _errorMessage.value = ex.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getNextPage() {
        val nextPage = (_currentPage.value ?: 1) + 1
        _currentPage.value = nextPage
        getCharacters(nextPage)
    }

    fun getPrevPage() {
        val prevPage = (_currentPage.value ?: 1) - 1
        _currentPage.value = prevPage
        getCharacters(prevPage)
    }
    companion object{
        const val TOTAL_PAGES = 42
    }
}
