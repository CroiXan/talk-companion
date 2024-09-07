package com.example.talkcompanion.feature.phrase.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.talkcompanion.feature.phrase.functions.addPhraseList
import com.example.talkcompanion.feature.phrase.functions.getPhraseListByUserName

@Composable
fun PhraseManagerScreen(context: Context, innerPadding: PaddingValues){

    var phraseList by remember { mutableStateOf(getPhraseListByUserName(context)) }
    var newPhrase by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)) {

        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            TextField(
                value = newPhrase,
                onValueChange = {
                    newPhrase = it
                },
                label = {
                    Text("Frase")
                },
                modifier = Modifier.padding(4.dp)
            )
            Button(
                onClick = {
                    if (newPhrase !== "") {
                        phraseList = addPhraseList(context,newPhrase)
                    }
                },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(text = "Guardar")
            }
        }

        LazyColumn(
            modifier = Modifier.padding(4.dp)
        ) {
            items(phraseList) { phrase ->
                Text(text = phrase.phrase)
            }
        }
    }

}