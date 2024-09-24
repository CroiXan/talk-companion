package com.example.talkcompanion.data.model

import android.app.Application
import androidx.room.Room
import com.example.talkcompanion.data.model.user.UserDatabase

class StoreApp : Application(){
    companion object{
        lateinit var userDatabase: UserDatabase
    }

    override fun onCreate() {
        super.onCreate()

        userDatabase = Room.databaseBuilder(this, UserDatabase::class.java,"store_database").build()
    }
}