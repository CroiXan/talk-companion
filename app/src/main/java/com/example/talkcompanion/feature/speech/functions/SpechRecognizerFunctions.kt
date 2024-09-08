package com.example.talkcompanion.feature.speech.functions

import android.content.Intent
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.util.Log

fun recognitionListener(): RecognitionListener{
    return object : RecognitionListener {
        override fun onReadyForSpeech(p0: Bundle?) {
            Log.d("Recognition","Escuchando...")
        }

        override fun onBeginningOfSpeech() {

        }

        override fun onRmsChanged(p0: Float) {

        }

        override fun onBufferReceived(p0: ByteArray?) {

        }

        override fun onEndOfSpeech() {

        }

        override fun onError(p0: Int) {

        }

        override fun onResults(results: Bundle)  {
            val data: ArrayList<String>? = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            println("Speech recognition results received: $data")
            if (data != null) {
                Log.d("Recognition",data[0])
            } else {
                Log.d("Recognition","No se pudo reconocer el texto")
            }
        }

        override fun onPartialResults(p0: Bundle?) {

        }

        override fun onEvent(p0: Int, p1: Bundle?) {

        }

    }
}

fun startSpeechRecognition(speechRecognizer: SpeechRecognizer) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-CL")
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...")

    speechRecognizer.startListening(intent)
}