package com.example.talkcompanion.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.talkcompanion.data.model.phrase.PhraseEntity

class UserPhraseViewModel: ViewModel() {
    private val _userPhrases = MutableLiveData<List<PhraseEntity>>(emptyList())
    val userPhrases: LiveData<List<PhraseEntity>> = _userPhrases

    fun updatePhraseList(newPhraseList: List<PhraseEntity>){
        _userPhrases.value = newPhraseList
    }
}