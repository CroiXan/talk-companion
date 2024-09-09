package com.example.talkcompanion.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SpeechResultViewModel: ViewModel() {
    private val _result = MutableLiveData("Dashboard")
    val speechResult: LiveData<String> = _result

    fun updateSpeechResult(result: String){
        _result.value = result
    }
}