package com.example.talkcompanion.feature.login.functions

import android.content.Context
import com.example.talkcompanion.feature.usermanagement.functions.userList

fun saveCurrentUserName(context: Context, key: String, value: String) {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key, value)
    editor.apply()
}

fun getCurrentUserName(context: Context, key: String): String? {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, null)
}

fun removeCurrentUserName(context: Context, key: String) {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.remove(key)
    editor.apply()
}

fun doLogin(userName: String, password: String, context: Context): Boolean {
    val userLists = userList()
    val user = userLists.find { it.userName == userName && it.password == password }
    if (user != null) {
        saveCurrentUserName(context, "currentUserName", userName)
    }
    return user != null
}

fun doLogout(context: Context){
    removeCurrentUserName(context, "currentUserName")
}

fun isLoggedIn(context: Context): Boolean {
    val currentUserName = getCurrentUserName(context, "currentUserName")
    return currentUserName != null
}