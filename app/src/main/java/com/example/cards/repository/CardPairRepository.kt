package com.example.cards.repository

import androidx.annotation.WorkerThread
import com.example.cards.db.CardPair
import com.example.cards.db.CardPairDao
import kotlinx.coroutines.flow.Flow

class CardPairRepository(private val cardPairDao: CardPairDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<CardPair>> = cardPairDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(pair: CardPair) {
        cardPairDao.insertWord(pair)
    }

    @WorkerThread
    suspend fun deleteCard(pair: CardPair){
        cardPairDao.deleteWord(pair)
    }
}