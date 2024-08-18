package com.example.talkcompanion.feature.speech.functions

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SpeechViewModel: ViewModel() {
    val speechText = mutableStateOf("")

    fun updateRecognizedText(text: String) {
        speechText.value = text
    }
}
