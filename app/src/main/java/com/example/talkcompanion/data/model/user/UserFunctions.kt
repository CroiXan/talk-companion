package com.example.talkcompanion.data.model.user

import com.example.talkcompanion.data.model.StoreApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun getUser(){
    CoroutineScope(Dispatchers.IO).launch{
        val user = StoreApp.userDatabase.userDao().getAllUsers()
    }
}