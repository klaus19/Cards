package com.example.cards.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class CardPair(
    @PrimaryKey @ColumnInfo(name = "front") val front: String,
    @ColumnInfo(name = "back") val back: String
)
