package com.example.talkcompanion.feature.login.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecuperarScreen(context: Context, innerPadding: PaddingValues){

    var email by remember {
        mutableStateOf("")
    }

    var codigo by remember {
        mutableStateOf("")
    }

    var contrasena by remember {
        mutableStateOf("")
    }

    var repetirContrasena by remember {
        mutableStateOf("")
    }

    var mostrarCamposEnvio by rememberSaveable { mutableStateOf(true) }

    var mostrarCamposComprobarCodigo by rememberSaveable { mutableStateOf(false) }

    var mostrarCamposCambioContrasena by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(mostrarCamposEnvio) {
            Text(text = "Recuperar Contraseña", fontSize = 24.sp)
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
            Button(
                onClick = {
                    mostrarCamposEnvio = false
                    mostrarCamposComprobarCodigo = true},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Enviar codigo")
            }
        }

        if(mostrarCamposComprobarCodigo) {
            Text(text = "Comprobar codigo", fontSize = 24.sp)
            TextField(
                value = codigo,
                onValueChange = {
                    codigo = it
                },
                label = {
                    Text("Codigo")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Button(
                onClick = {
                        if(codigo == "123456"){
                            mostrarCamposComprobarCodigo = false
                            mostrarCamposCambioContrasena = true
                            Toast.makeText(context, "Codigo correcto", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "Codigo incorrecto", Toast.LENGTH_SHORT).show()
                        }
                    },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Comprobar codigo")
            }
        }

        if(mostrarCamposCambioContrasena) {
            Text(text = "Nueva Contraseña", fontSize = 24.sp)
            TextField(
                value = contrasena,
                onValueChange = {
                    contrasena = it
                },
                label = {
                    Text("Contraseña")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            TextField(
                value = repetirContrasena,
                onValueChange = {
                    repetirContrasena = it
                },
                label = {
                    Text("Repetir Contraseña")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }

}