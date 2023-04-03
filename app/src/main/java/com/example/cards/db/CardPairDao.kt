package com.example.cards.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CardPairDao {

    @Query("SELECT * FROM word_table ORDER BY front ASC, back ASC")
    fun getAlphabetizedWords(): Flow<List<CardPair>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(pair: CardPair)

    @Delete
    suspend fun deleteWord(pair: CardPair)

}