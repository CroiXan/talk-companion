 package com.example.talkcompanion.data.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM user WHERE user_name = :userName and password = :password")
    suspend fun getUserByNameAndPass(userName: String, password: String): List<UserEntity>

    @Query("DELETE FROM user WHERE user_name = :userName")
    suspend fun deleteUserByUserName(userName: String)
}