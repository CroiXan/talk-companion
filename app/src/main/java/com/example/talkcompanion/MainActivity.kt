package com.example.talkcompanion

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.talkcompanion.common.components.DashboardMockScreen
import com.example.talkcompanion.common.components.TopBarComponent
import com.example.talkcompanion.data.model.ModalViewModel
import com.example.talkcompanion.data.model.SpeechResultViewModel
import com.example.talkcompanion.data.model.UserPhraseViewModel
import com.example.talkcompanion.feature.login.functions.isLoggedIn
import com.example.talkcompanion.feature.phrase.functions.getFirebasePhraseListByUserName
import com.example.talkcompanion.feature.phrase.functions.getPhraseListByUserName
import com.example.talkcompanion.feature.speech.functions.destroyTextToSpeech
import com.example.talkcompanion.feature.speech.functions.languageNotFoundException
import com.example.talkcompanion.feature.speech.functions.recognitionListener
import com.example.talkcompanion.feature.speech.functions.speechRecognitionNotInitException
import com.example.talkcompanion.ui.theme.TalkCompanionTheme
import com.google.firebase.FirebaseApp
import java.util.Locale

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener  {
    private lateinit var tts: TextToSpeech
    private lateinit var userPhrases: UserPhraseViewModel
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var speechResult: SpeechResultViewModel
    private lateinit var modalValues: ModalViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        val REQUEST_CODE = 0

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                REQUEST_CODE)

        }

        userPhrases = ViewModelProvider(this).get(UserPhraseViewModel::class.java)
        speechResult = ViewModelProvider(this).get(SpeechResultViewModel::class.java)
        modalValues = ViewModelProvider(this).get(ModalViewModel::class.java)
        tts = TextToSpeech(this, this)

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(recognitionListener(speechResult))

        enableEdgeToEdge()
        checkSession()
        setContent {
            TalkCompanionTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                val showModal by modalValues.showModal.observeAsState(false)
                val dialogMesage by modalValues.message.observeAsState("")

                Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = { TopBarComponent(true,
                            showBack = false,
                            context = this,
                            scrollBehavior = scrollBehavior, onArrowBack = { finish() }) }) { innerPadding ->

                    DashboardMockScreen(innerPadding,this,tts,userPhrases,speechRecognizer,speechResult)

                    if(showModal){
                        AlertDialogExample({modalValues.updateShowModal(false)},
                            "Error",
                            dialogMesage,
                            Icons.Default.Info)
                    }
                }
            }
        }
    }

    private fun checkSession(){
        if(!isLoggedIn()){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onInit(status: Int) {
        FirebaseApp.initializeApp(this)

        getFirebasePhraseListByUserName(){ result ->
            userPhrases.updatePhraseList(result)
        }

        try{

            if (status == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale("es", "CL"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    throw languageNotFoundException()
                }
            } else {
                throw speechRecognitionNotInitException()
            }
        }catch (error: Throwable){
            modalValues.updateShowModal(true)
            modalValues.updateMessage(error.message.toString())
        }

    }

    override fun onDestroy() {
        destroyTextToSpeech(tts)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        getFirebasePhraseListByUserName(){ result ->
            userPhrases.updatePhraseList(result)
        }
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {

        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cerrar")
            }
        }
    )
}