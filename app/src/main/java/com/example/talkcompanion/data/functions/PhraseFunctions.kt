package com.example.talkcompanion.data.functions

import android.content.Context
import com.example.talkcompanion.data.model.Phrase
import com.example.talkcompanion.feature.login.functions.getCurrentUserName
import com.example.talkcompanion.feature.usermanagement.functions.getPhraseListMock
import com.example.talkcompanion.feature.usermanagement.functions.savePhraseListMock
import com.google.gson.Gson

fun getPhraseListByUserName(context: Context): List<Phrase> {
    val userName = getCurrentUserName(context, "currentUserName")
    return getPhraseList(context).filter { it.userName == userName }.sortedBy { it.orderNumer }
}

private fun getPhraseList(context: Context): ArrayList<Phrase> {
    val gson = Gson()
    val json = getPhraseListMock(context)
    if(json != null){
        return gson.fromJson(json, Array<Phrase>::class.java).toCollection(ArrayList())
    }
    return ArrayList<Phrase>()
}

fun addPhraseList(context: Context, newPhrase: String){
    val userName = getCurrentUserName(context, "currentUserName")?:""
    val userPhraseList = getPhraseListByUserName(context)
    val phraseList = getPhraseList(context)
    val maxId = phraseList.maxBy { it.id }.id
    val newPhraseItem = Phrase(maxId + 1, userName, newPhrase, userPhraseList.size + 1)
    phraseList.add(newPhraseItem)
    savePhraseListMock(context, Gson().toJson(phraseList))
}
