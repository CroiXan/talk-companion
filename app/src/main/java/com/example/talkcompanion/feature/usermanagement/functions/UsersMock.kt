package com.example.talkcompanion.feature.usermanagement.functions

import com.example.talkcompanion.data.model.User

fun userList(): ArrayList<User>{
    var userList = arrayListOf(
        User("prueba", "", "prueba", "", "", "", ""),
        User("prueba2", "", "prueba2", "", "", "", ""))
    return userList
}