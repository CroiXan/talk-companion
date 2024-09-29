package com.example.talkcompanion.data.model.phrase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhraseDao {

    @Insert
    suspend fun insertPhrase(phrase: PhraseEntity): Long

    @Query("DELETE FROM phrase WHERE id = :id")
    suspend fun deletePhraseById(id: Int)

    @Query("SELECT * FROM phrase WHERE userId = :userId")
    suspend fun getPhraseByUserId(userId: String)
}