package com.example.talkcompanion.feature.usermanagement.functions

import android.content.Context
import com.example.talkcompanion.data.model.User
import com.google.gson.Gson

/*fun userList(): ArrayList<User>{
    return defaultUserList()
}*/

fun userList(context: Context): ArrayList<User>{
    val gson = Gson()
    val json = getUserList(context)
    if(json != null){
        return gson.fromJson(json, Array<User>::class.java).toCollection(ArrayList())
    }
    saveUserList(context, gson.toJson(defaultUserList()))
    return defaultUserList()
}

fun createOrUpdateUser(context: Context, user: User){
    val userList = userList(context)
    val index = userList.indexOfFirst { it.userName == user.userName || it.email == user.email }
    if (index != -1) {
        userList[index] = user
    } else if(user.userName != "" && user.email != "" && user.password != "") {
        userList.add(user)
    }
    saveUserList(context, Gson().toJson(userList))
}

private fun defaultUserList(): ArrayList<User>{
    val userList = arrayListOf(
        User("prueba", "prueba@mail.com", "prueba", "Juan", "Perez", ""),
        User("prueba2", "prueba2@mail.com", "prueba2", "Pedro", "Flores", ""))
    return userList
}

private fun saveUserList(context: Context, value: String) {
    val sharedPreferences = context.getSharedPreferences("UserList", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("Json", value)
    editor.apply()
}

private fun getUserList(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("UserList", Context.MODE_PRIVATE)
    return sharedPreferences.getString("Json", null)
}