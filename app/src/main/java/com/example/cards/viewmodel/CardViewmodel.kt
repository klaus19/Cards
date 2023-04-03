package com.example.cards.viewmodel

import androidx.lifecycle.*
import com.example.cards.db.CardPair
import com.example.cards.repository.CardPairRepository
import kotlinx.coroutines.launch

class CardViewmodel(private val repository:CardPairRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<CardPair>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertCards(pair: CardPair) = viewModelScope.launch {
        repository.insert(pair)
    }

    fun deleteCards(pair: CardPair) = viewModelScope.launch {
        repository.deleteCard(pair)
    }
}

class WordViewModelFactory(private val repository:CardPairRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CardViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}