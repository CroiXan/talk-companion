package com.example.talkcompanion

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.talkcompanion.common.components.TopBarComponent
import com.example.talkcompanion.feature.login.components.LoginScreen
import com.example.talkcompanion.feature.login.functions.doLogout
import com.example.talkcompanion.feature.login.functions.isLoggedIn
import com.example.talkcompanion.feature.speech.components.SpeechScreen
import com.example.talkcompanion.feature.speech.functions.SpeechViewModel
import com.example.talkcompanion.feature.speech.functions.destroyTextToSpeech
import com.example.talkcompanion.ui.theme.TalkCompanionTheme
import java.util.Locale

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private lateinit var speechRecognizer: SpeechRecognizer
    private val speechViewModel: SpeechViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tts = TextToSpeech(this, this)

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                speechViewModel.updateRecognizedText("Escuchando...")
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
                    speechViewModel.updateRecognizedText(data[0])
                } else {
                    speechViewModel.updateRecognizedText("No se pudo reconocer el texto")
                }
            }

            override fun onPartialResults(p0: Bundle?) {

            }

            override fun onEvent(p0: Int, p1: Bundle?) {

            }

        })

        enableEdgeToEdge()
        setContent {
            TalkCompanionTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = { TopBarComponent(
                            showMenu = true,
                            showBack = false,
                            context = this,
                            scrollBehavior = scrollBehavior, onArrowBack = { finish() }) }) { innerPadding ->

                    SpeechScreen(innerPadding,startSpeechRecognition = { startSpeechRecognition() }, speechViewModel, tts)

                }
            }
        }
    }

    override fun onInit(status: Int) {
        checkSession()

        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale("es", "CL"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                println("Idioma no soportado o datos faltantes")
            }
        } else {
            println("Inicialización de TextToSpeech fallida")
        }

    }


    override fun onDestroy() {
        destroyTextToSpeech(tts)
        speechRecognizer.destroy()
        super.onDestroy()
    }

    private fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-CL")
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...")

        speechRecognizer.startListening(intent)
    }

    private fun checkSession(){
        if(!isLoggedIn(this)){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
