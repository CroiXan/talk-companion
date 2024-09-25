package com.example.talkcompanion.feature.login.functions

import android.content.Context
import com.example.talkcompanion.common.TalkCompanionApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private fun saveCurrentUserName(context: Context, value: String) {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("currentUserName", value)
    editor.apply()
}

private fun getCurrentUserName(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("currentUserName", null)
}

private fun removeCurrentUserName(context: Context) {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.remove("currentUserName")
    editor.apply()
}

fun doLogin(userName: String, password: String, context: Context, callback: (result: Boolean) -> Unit){
    CoroutineScope(Dispatchers.IO).launch{
        val user = TalkCompanionApp.userDatabase.userDao().getUserByNameAndPass(userName,password)
        if (user.isNotEmpty()) {
            saveCurrentUserName(context, userName)
        }
        callback(user.isNotEmpty())
    }
}

fun doLogout(context: Context){
    CoroutineScope(Dispatchers.IO).launch{
        val userName = getCurrentUserName(context)
        if (userName != null) {
            TalkCompanionApp.userDatabase.userDao().deleteUserByUserName(userName)
        }
        removeCurrentUserName(context)
    }

}

fun isLoggedIn(context: Context): Boolean{
    val currentUserName = getCurrentUserName(context)
    return currentUserName != null
}