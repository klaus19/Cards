package com.example.cards

import android.app.Application
import com.example.cards.db.CardRoomDatabase
import com.example.cards.repository.CardPairRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CardsApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { CardRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy {CardPairRepository(database.cardPairDao()) }
}