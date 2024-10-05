package com.example.talkcompanion.data.model.user

import com.example.talkcompanion.common.TalkCompanionApp
import com.example.talkcompanion.data.model.phrase.PhraseEntity
import com.google.firebase.auth.FirebaseAuth
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

fun getLoggedUser(callback: (result: UserEntity) -> Unit){
    CoroutineScope(Dispatchers.IO).launch{
        val userId = FirebaseAuth.getInstance().currentUser?.uid!!
        val users = TalkCompanionApp.userDatabase.userDao().getUserById(userId)
        val user = users.first { it.id == userId }
        callback(user)
    }
}

fun addUser(user: UserEntity){
    CoroutineScope(Dispatchers.IO).launch{
        TalkCompanionApp.userDatabase.userDao().insertUser(user)
    }
}