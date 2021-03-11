package com.ricardo.roomejemplo

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class PalabraViewModel(private val repository: PalabraRepository) : ViewModel() {
    val allWords: LiveData<List<Palabra>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(palabra: Palabra) = viewModelScope.launch {
        repository.insert(palabra)
    }
}

class PalabraViewModelFactory(private val repository: PalabraRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PalabraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PalabraViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}