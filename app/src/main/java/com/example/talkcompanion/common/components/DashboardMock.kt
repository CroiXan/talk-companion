package com.example.talkcompanion.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

@Composable
fun DashboardMockScreen(innerPadding: PaddingValues) {
    var frase by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(top = 20.dp)) {

        Text(text = "Dashboard",
            fontSize = 24.sp,
            modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.padding(20.dp)
        ){
            Button(onClick = {  },
                modifier = Modifier.padding(4.dp)) {
                Text(text = "Frase3")
            }
            Button(onClick = {  },
                modifier = Modifier.padding(4.dp)) {
                Text(text = "Frase2")
            }
            Button(onClick = {  },
                modifier = Modifier.padding(4.dp)) {
                Text(text = "Frase3")
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