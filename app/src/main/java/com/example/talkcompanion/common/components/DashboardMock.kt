package com.example.talkcompanion.common.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.unit.sp
import com.example.talkcompanion.feature.phrase.functions.getPhraseListByUserName

@Composable
fun DashboardMockScreen(innerPadding: PaddingValues, context: Context) {
    var frase by remember { mutableStateOf("") }

    val userPhrases = getPhraseListByUserName(context);

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(top = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween) {

        Row(
            modifier = Modifier.padding(4.dp)
        ){
            Text(text = "Dashboard",
                fontSize = 24.sp,
                modifier = Modifier.padding(10.dp))
        }

        Row(
            modifier = Modifier.padding(4.dp)
        ){
            Button(onClick = {  },
                modifier = Modifier.padding(4.dp)) {
                Text(text = "Escuchar")
            }
        }

        Row(
            modifier = Modifier.padding(4.dp)
        ){
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(userPhrases) { phrase ->
                    Button(onClick = {  },
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
            Button(onClick = {  },
                modifier = Modifier.padding(4.dp)) {
                Text(text = "Frase a Audio")
            }
        }
    }
}