package com.example.talkcompanion.data.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Suppress("AndroidUnresolvedRoomSqlReference")
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>
}