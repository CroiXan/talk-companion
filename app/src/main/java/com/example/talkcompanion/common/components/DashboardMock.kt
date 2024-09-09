package com.example.talkcompanion.common.components

import android.content.Context
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talkcompanion.data.model.SpeechResultViewModel
import com.example.talkcompanion.data.model.UserPhraseViewModel
import com.example.talkcompanion.feature.speech.functions.speak
import com.example.talkcompanion.feature.speech.functions.startSpeechRecognition

@Composable
fun DashboardMockScreen(innerPadding: PaddingValues, context: Context, textToSpeechInstance: TextToSpeech, newUserPhrases: UserPhraseViewModel, speechRecognizer: SpeechRecognizer, speechResult: SpeechResultViewModel) {
    var frase by remember { mutableStateOf("") }
    val resultado by speechResult.speechResult.observeAsState("Dashboard")
    val userPhrases by newUserPhrases.userPhrases.observeAsState(emptyList())

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(top = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween) {

        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = resultado,
                fontSize = 24.sp,
                modifier = Modifier.padding(10.dp))
        }

        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            ElevatedButton(onClick = { startSpeechRecognition(speechRecognizer) },
                modifier = Modifier
                    .padding(4.dp)
                    .size(width = 150.dp, height = 150.dp),
                shape = RoundedCornerShape(100,100,100,100)) {
                Text(text = "Escuchar")
            }
        }

        Row(
            modifier = Modifier.padding(4.dp)
        ){
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(userPhrases) { phrase ->
                    Button(onClick = { speak(phrase.phrase, textToSpeechInstance) },
                        modifier = Modifier.padding(4.dp)) {
                        Text(text = phrase.phrase)
                    }
                }
            }
        }

        Row(
            modifier = Modifier.padding(4.dp)
        ){
            TextField(
                value = frase,
                onValueChange = {
                    frase = it
                },
                label = {
                    Text("Frase")
                },
                modifier = Modifier.padding(4.dp)

            )

            Button(onClick = { speak(frase, textToSpeechInstance) },
                shape = RoundedCornerShape(0,20,20,0),
                modifier = Modifier.padding(4.dp)) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Localized description"
                )
            }
        }
    }
}