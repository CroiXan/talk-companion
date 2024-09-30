package com.example.talkcompanion.feature.speech.functions

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.example.talkcompanion.data.model.SpeechResultViewModel

fun recognitionListener(speechResult: SpeechResultViewModel): RecognitionListener{
    return object : RecognitionListener {
        override fun onReadyForSpeech(p0: Bundle?) {
            speechResult.updateSpeechResult("Escuchando...")
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
                speechResult.updateSpeechResult(data[0])
            } else {
                speechResult.updateSpeechResult("No se pudo reconocer el texto, Intentelo denuevo")
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

class languageNotFoundException(): Exception("Idioma de reconocimiento de voz no esta disponible")
class speechRecognitionNotInitException(): Exception("Reconocimiento de voz no se logro inicializar")