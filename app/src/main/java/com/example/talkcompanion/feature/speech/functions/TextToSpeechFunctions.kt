package com.example.talkcompanion.feature.speech.functions

import android.speech.tts.TextToSpeech

fun speak(text: String, textToSpeechInstance: TextToSpeech) {
    textToSpeechInstance.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
}

fun destroyTextToSpeech(textToSpeechInstance: TextToSpeech) {
    if (textToSpeechInstance.isSpeaking) {
        textToSpeechInstance.stop()
    }
    textToSpeechInstance.shutdown()
}