package com.example.talkcompanion.feature.phrase.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.talkcompanion.data.model.Phrase
import com.example.talkcompanion.data.model.UserPhraseViewModel
import com.example.talkcompanion.data.model.phrase.PhraseEntity
import com.example.talkcompanion.feature.phrase.functions.addFirebasePhraseList
import com.example.talkcompanion.feature.phrase.functions.addPhraseList
import com.example.talkcompanion.feature.phrase.functions.deleteFirebasePhraseById
import com.example.talkcompanion.feature.phrase.functions.deletePhraseById
import com.example.talkcompanion.feature.phrase.functions.getPhraseListByUserName
import com.example.talkcompanion.feature.phrase.functions.updateFirebaseUserPhrases
import com.example.talkcompanion.feature.phrase.functions.updateUserPhrases

@Composable
fun PhraseManagerScreen(context: Context, innerPadding: PaddingValues, userPhrases: UserPhraseViewModel){

    val phraseList by userPhrases.userPhrases.observeAsState(emptyList())
    var newPhrase by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        phraseList.sortedBy { it.orderNumber }
    }

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
                        addFirebasePhraseList(newPhrase,phraseList){ result ->
                            userPhrases.updatePhraseList(result)
                        }
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
                        Text(text = phrase.phrase!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(4.dp))
                        /*Text(text = (phrase.orderNumer.toString()),
                            modifier = Modifier.padding(4.dp))*/

                        Spacer(Modifier.weight(1f))

                        OutlinedButton(
                            modifier = Modifier.padding(
                                start = 0.dp,
                                top = 4.dp,
                                end = 4.dp,
                                bottom = 4.dp),
                            shape = RoundedCornerShape(100),
                            onClick = {
                                deleteFirebasePhraseById(phrase.id,phraseList){ result ->
                                    userPhrases.updatePhraseList(result)
                                }
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Borrar Frase"
                            )
                        }
                        OutlinedButton(
                            modifier = Modifier.padding(
                                start = 4.dp,
                                top = 4.dp,
                                end = 0.dp,
                                bottom = 4.dp),
                            shape = RoundedCornerShape(20,0,0,20),
                            onClick = {
                                userPhrases.updatePhraseList(changePhrasePosition(phraseList, phrase.id, false))
                            }) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = "Bajar Frase"
                            )
                        }
                        OutlinedButton(
                            modifier = Modifier.padding(
                                start = 0.dp,
                                top = 4.dp,
                                end = 4.dp,
                                bottom = 4.dp),
                            shape = RoundedCornerShape(0,20,20,0),
                            onClick = {
                                userPhrases.updatePhraseList(changePhrasePosition(phraseList, phrase.id, true))
                            }) {
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

private fun changePhrasePosition( phraseList: List<PhraseEntity>,phraseId: Int, isUp: Boolean): List<PhraseEntity>{
    val indexOfPhraseId = phraseList.indexOfFirst { it.id == phraseId }
    Log.d("changePhrasePosition","index ${indexOfPhraseId} phraseId ${phraseId}")
    if (indexOfPhraseId >= 0){
        if (isUp){
            val indexUpperPhrase = phraseList.indexOfFirst { it.orderNumber == phraseList[indexOfPhraseId].orderNumber!! - 1 }
            if(indexUpperPhrase >= 0){
                phraseList[indexOfPhraseId].orderNumber = phraseList[indexOfPhraseId].orderNumber!! - 1
                phraseList[indexUpperPhrase].orderNumber = phraseList[indexOfPhraseId].orderNumber!! + 1
            }

        }else{
            val indexLowerPhrase = phraseList.indexOfFirst { it.orderNumber == phraseList[indexOfPhraseId].orderNumber!! + 1 }
            if(indexLowerPhrase >= 0) {
                phraseList[indexOfPhraseId].orderNumber = phraseList[indexOfPhraseId].orderNumber!! + 1
                phraseList[indexLowerPhrase].orderNumber = phraseList[indexOfPhraseId].orderNumber!! - 1
            }
        }
    }

    //updateUserPhrases(context, phraseList)
    updateFirebaseUserPhrases(phraseList){}

    return phraseList.sortedBy { it.orderNumber }
}