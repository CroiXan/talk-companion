package com.example.talkcompanion.feature.usermanagement.functions

import android.content.Context

fun savePhraseListMock(context: Context, value: String) {
    val sharedPreferences = context.getSharedPreferences("PhraseList", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("Json", value)
    editor.apply()
}

fun getPhraseListMock(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("PhraseList", Context.MODE_PRIVATE)
    return sharedPreferences.getString("Json", null)
}
