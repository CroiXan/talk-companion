package com.example.talkcompanion.feature.usermanagement.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talkcompanion.data.model.UserViewModel
import com.example.talkcompanion.data.model.user.UserEntity
import com.example.talkcompanion.data.model.user.getUser
import com.example.talkcompanion.feature.login.functions.getCurrentUser

@Composable
fun DetalleUsuarioScreen(innerPadding: PaddingValues, userView: UserViewModel){

    val currentUser by userView.user.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hola, ${currentUser?.userName}", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Email: ${currentUser?.email}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Nombre: ${currentUser?.firstName}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Apellido: ${currentUser?.lastName}", fontSize = 16.sp)
    }

}