package com.example.talkcompanion.data.model.user

import com.example.talkcompanion.common.TalkCompanionApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun getUser(){
    CoroutineScope(Dispatchers.IO).launch{
        val user = TalkCompanionApp.userDatabase.userDao().getAllUsers()
    }
}

fun getUserTryLogin(){
    CoroutineScope(Dispatchers.IO).launch{
        val user = TalkCompanionApp.userDatabase.userDao().getAllUsers()
    }
}