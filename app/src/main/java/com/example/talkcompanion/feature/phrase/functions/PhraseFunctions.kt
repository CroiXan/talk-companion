package com.example.talkcompanion.feature.phrase.functions

import android.content.Context
import android.util.Log
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

fun addPhraseList(context: Context, newPhrase: String): List<Phrase>{
    val userName = getCurrentUserName(context, "currentUserName")?:""
    val userPhraseList = getPhraseListByUserName(context)
    val phraseList = getPhraseList(context)
    var maxId = 0
    if (phraseList.size > 0){
        maxId = phraseList.maxBy { it.id }.id
    }
    var orderNumber = 0
    if (userPhraseList.isNotEmpty()){
        orderNumber = userPhraseList.maxBy { it.orderNumer }.orderNumer
    }
    val newPhraseItem = Phrase(maxId + 1, userName, newPhrase, orderNumber + 1)
    phraseList.add(newPhraseItem)
    savePhraseListMock(context, Gson().toJson(phraseList))
    return userPhraseList + listOf(newPhraseItem)
}

fun updateUserPhrases(context: Context, userPhrases: List<Phrase>){
    val phraseList = getPhraseList(context)

    for (currentPhrase in userPhrases){
        val indexOfPhraseId = phraseList.indexOfFirst { it.id == currentPhrase.id }
        if(indexOfPhraseId >= 0){
            phraseList[indexOfPhraseId].phrase = currentPhrase.phrase
            phraseList[indexOfPhraseId].orderNumer = currentPhrase.orderNumer
        }
    }

    savePhraseListMock(context, Gson().toJson(phraseList))
}

fun deletePhraseById(context: Context, phraseId: Int): List<Phrase>{
    updatePhrasePositionsBeforeDelete(context,phraseId)
    val phraseList = getPhraseList(context)
    val resultList = phraseList.filter { it.id != phraseId }.toTypedArray()
    savePhraseListMock(context, Gson().toJson(resultList))
    return getPhraseListByUserName(context)
}

private fun updatePhrasePositionsBeforeDelete(context: Context, phraseId: Int){
    val userPhraseList = getPhraseListByUserName(context)
    val indexOfPhraseId = userPhraseList.indexOfFirst { it.id == phraseId }

    for (currentPhrase in userPhraseList){
        if (userPhraseList[indexOfPhraseId].orderNumer < currentPhrase.orderNumer){
            currentPhrase.orderNumer -= 1
        }
    }

    updateUserPhrases(context, userPhraseList)
}