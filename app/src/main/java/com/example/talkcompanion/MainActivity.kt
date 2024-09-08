package com.example.talkcompanion

import android.content.Intent
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.talkcompanion.common.components.DashboardMockScreen
import com.example.talkcompanion.common.components.TopBarComponent
import com.example.talkcompanion.data.model.Phrase
import com.example.talkcompanion.data.model.UserPhraseViewModel
import com.example.talkcompanion.feature.login.functions.isLoggedIn
import com.example.talkcompanion.feature.phrase.functions.getPhraseListByUserName
import com.example.talkcompanion.feature.speech.functions.destroyTextToSpeech
import com.example.talkcompanion.feature.speech.functions.recognitionListener
import com.example.talkcompanion.ui.theme.TalkCompanionTheme
import java.util.Locale

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener  {
    private lateinit var tts: TextToSpeech
    private lateinit var userPhrases: UserPhraseViewModel
    private lateinit var speechRecognizer: SpeechRecognizer

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPhrases = ViewModelProvider(this).get(UserPhraseViewModel::class.java)
        tts = TextToSpeech(this, this)

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(recognitionListener())

        enableEdgeToEdge()
        checkSession()
        setContent {
            TalkCompanionTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = { TopBarComponent(true,
                            showBack = false,
                            context = this,
                            scrollBehavior = scrollBehavior, onArrowBack = { finish() }) }) { innerPadding ->

                    DashboardMockScreen(innerPadding,this,tts,userPhrases,speechRecognizer)

                }
            }
        }
    }

    private fun checkSession(){
        if(!isLoggedIn(this)){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onInit(status: Int) {

        userPhrases.updatePhraseList(getPhraseListByUserName(this))

        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale("es", "CL"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //presentar error de idioma
            }
        } else {
            //presentar error de inicializacion
        }
    }

    override fun onDestroy() {
        destroyTextToSpeech(tts)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        this.userPhrases.updatePhraseList(getPhraseListByUserName(this))
    }
}


