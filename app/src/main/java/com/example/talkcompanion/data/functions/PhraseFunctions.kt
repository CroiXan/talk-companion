package com.example.talkcompanion.data.functions

import android.content.Context
import com.example.talkcompanion.data.model.Phrase
import com.example.talkcompanion.feature.usermanagement.functions.getPhraseListMock
import com.google.gson.Gson

fun getPhraseListByUserName(context: Context, userName: String){
    
}

fun getPhraseList(context: Context): ArrayList<Phrase> {
    val gson = Gson()
    val json = getPhraseListMock(context)
    if(json != null){
        return gson.fromJson(json, Array<Phrase>::class.java).toCollection(ArrayList())
    }
    return ArrayList<Phrase>()
}

fun updatePhraseList(){

}
