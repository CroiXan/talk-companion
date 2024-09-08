package com.example.talkcompanion.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserPhraseViewModel: ViewModel() {
    private val _userPhrases = MutableLiveData<List<Phrase>>(emptyList())
    val userPhrases: LiveData<List<Phrase>> = _userPhrases

    fun updatePhraseList(newPhraseList: List<Phrase>){
        _userPhrases.value = newPhraseList
    }
}