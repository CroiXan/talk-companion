package com.example.talkcompanion.common

import android.app.Application
import androidx.room.Room
import com.example.talkcompanion.data.model.user.UserDatabase
import com.google.firebase.FirebaseApp

class TalkCompanionApp : Application(){
    companion object {
        lateinit var userDatabase: UserDatabase
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        userDatabase = Room.databaseBuilder(this, UserDatabase::class.java,"store_database").build()
    }
}