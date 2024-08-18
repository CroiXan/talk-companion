package com.example.talkcompanion.feature.speech.components

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.talkcompanion.feature.speech.functions.SpeechViewModel

@Composable
fun SpeechScreen(innerPadding: PaddingValues, startSpeechRecognition: () -> Unit, speechViewModel: SpeechViewModel,tts: TextToSpeech) {
    var textFielValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = speechViewModel.speechText.value,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = { startSpeechRecognition() }) {
            Text("Comenzar interpretación de voz")
        }

        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 20.dp))

        Text(
            text = "Frases de uso rapido",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row {
            Button(onClick = {
                reproducirAudio("Hola", tts) },
                modifier = Modifier.padding(8.dp)) {
                Text("Hola")
            }

            Button(onClick = {
                reproducirAudio("Gracias", tts) },
                modifier = Modifier.padding(8.dp)) {
                Text("Gracias")
            }

            Button(onClick = {
                reproducirAudio("Adios", tts) },
                modifier = Modifier.padding(8.dp)) {
                Text("Adios")
            }
        }

        Button(onClick = {
            reproducirAudio("Esta aplicacion me asiste en la comunicacion", tts)
        }) {
            Text("Esta aplicacion me asiste en la comunicacion")
        }

        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 20.dp))

        Text(
            text = "Frases personalizadas",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row {
            TextField(
                value = textFielValue,
                onValueChange = { textFielValue = it },
                label = { Text("Frase") },
                modifier = Modifier.padding(8.dp)
            )
            Button(onClick = {
                reproducirAudio(textFielValue, tts) },
                modifier = Modifier.padding(8.dp)) {
                Text("Emitir Audio")
            }
        }
    }
}

fun reproducirAudio(text: String, tts: TextToSpeech) {
    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
}