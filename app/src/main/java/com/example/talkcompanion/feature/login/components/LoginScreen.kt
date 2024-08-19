package com.example.talkcompanion.feature.login.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talkcompanion.MainActivity
import com.example.talkcompanion.RecuperarContrasenaActivity
import com.example.talkcompanion.RegistroActivity
import com.example.talkcompanion.feature.login.functions.doLogin

@Composable
fun LoginScreen(innerPadding: PaddingValues,context: Context) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", fontSize = 24. sp)

        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Contraseña")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                if( doLogin(email, password, context) ){
                    Toast.makeText(context, "Login correcto", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }else{
                    Toast.makeText(context, "Login incorrecto", Toast.LENGTH_SHORT).show()
                } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Login")
        }

        Row {
            Text(
                text = "Recuperar contraseña",
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(context, RecuperarContrasenaActivity::class.java)
                        context.startActivity(intent)
                    }
                    .padding(6.dp)
            )

            Text(
                text = "Registrarse",
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(context, RegistroActivity::class.java)
                        context.startActivity(intent)
                    }
                    .padding(6.dp)
            )
        }

    }
}

