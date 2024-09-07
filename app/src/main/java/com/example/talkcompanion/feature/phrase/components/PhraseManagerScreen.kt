package com.example.talkcompanion.feature.phrase.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.talkcompanion.data.model.Phrase
import com.example.talkcompanion.feature.phrase.functions.addPhraseList
import com.example.talkcompanion.feature.phrase.functions.deletePhraseById
import com.example.talkcompanion.feature.phrase.functions.getPhraseListByUserName
import com.example.talkcompanion.feature.phrase.functions.updateUserPhrases

@Composable
fun PhraseManagerScreen(context: Context, innerPadding: PaddingValues){

    var phraseList by remember { mutableStateOf(getPhraseListByUserName(context).sortedBy { it.orderNumer }) }
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

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)){
                    Row(modifier = Modifier.padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = phrase.phrase,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(4.dp))
                        Text(text = (phrase.orderNumer.toString()),
                            modifier = Modifier.padding(4.dp))

                        Spacer(Modifier.weight(1f))

                        Button(
                            modifier = Modifier.padding(
                                start = 0.dp,
                                top = 4.dp,
                                end = 4.dp,
                                bottom = 4.dp),
                            onClick = { phraseList = deletePhraseById(context, phrase.id) }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Borrar Frase"
                            )
                        }
                        Button(
                            modifier = Modifier.padding(
                                start = 4.dp,
                                top = 4.dp,
                                end = 0.dp,
                                bottom = 4.dp),
                            onClick = { phraseList = changePhrasePosition(context, phraseList, phrase.id, false) }) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = "Bajar Frase"
                            )
                        }
                        Button(
                            modifier = Modifier.padding(
                                start = 0.dp,
                                top = 4.dp,
                                end = 4.dp,
                                bottom = 4.dp),
                            onClick = { phraseList = changePhrasePosition(context, phraseList, phrase.id, true) }) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowUp,
                                contentDescription = "Subir Frase"
                            )
                        }
                    }

                }

            }
        }
    }

}

private fun changePhrasePosition(context: Context, phraseList: List<Phrase>,phraseId: Int, isUp: Boolean): List<Phrase>{
    val indexOfPhraseId = phraseList.indexOfFirst { it.id == phraseId }

    if (indexOfPhraseId >= 0){
        if (isUp){
            val indexUpperPhrase = phraseList.indexOfFirst { it.orderNumer == phraseList[indexOfPhraseId].orderNumer - 1 }
            if(indexUpperPhrase >= 0){
                phraseList[indexOfPhraseId].orderNumer -= 1
                phraseList[indexUpperPhrase].orderNumer += 1
            }

        }else{
            val indexLowerPhrase = phraseList.indexOfFirst { it.orderNumer == phraseList[indexOfPhraseId].orderNumer + 1 }
            if(indexLowerPhrase >= 0) {
                phraseList[indexOfPhraseId].orderNumer += 1
                phraseList[indexLowerPhrase].orderNumer -= 1
            }
        }
    }

    updateUserPhrases(context, phraseList)
    return phraseList.sortedBy { it.orderNumer }
}