package com.example.talkcompanion.feature.speech.functions

import android.speech.tts.TextToSpeech
import java.util.Locale

fun initTextToSpeech(status: Int, textToSpeechInstance: TextToSpeech) {
    if (status == TextToSpeech.SUCCESS) {
        val result = textToSpeechInstance.setLanguage(Locale("spa", "MEX")) //(Locale.US)

        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            println("Idioma no soportado o datos faltantes")
        } else {
            //speak("Hola Mundo, Bienvenido a nuestra app!",textToSpeechInstance)
        }
    } else {
        println("Inicialización de TextToSpeech fallida")
    }
}

fun speak(text: String, textToSpeechInstance: TextToSpeech) {
    textToSpeechInstance.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
}

fun destroyTextToSpeech(textToSpeechInstance: TextToSpeech) {
    if (textToSpeechInstance.isSpeaking) {
        textToSpeechInstance.stop()
    }
    textToSpeechInstance.shutdown()
}