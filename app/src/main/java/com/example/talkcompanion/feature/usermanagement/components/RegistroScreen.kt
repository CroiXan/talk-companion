package com.example.talkcompanion.feature.usermanagement.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talkcompanion.LoginActivity
import com.example.talkcompanion.data.model.User
import com.example.talkcompanion.feature.usermanagement.functions.createOrUpdateUser
import com.example.talkcompanion.feature.usermanagement.functions.registerUser

@Composable
fun RegistroScreen(innerPadding: PaddingValues, context: Context) {

    var userName by remember { mutableStateOf("") }
    var isUserNameEmpty by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var isEmailEmpty by remember { mutableStateOf(false) }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var isContrasenaEmpty by remember { mutableStateOf(false) }
    var repetirContrasena by remember { mutableStateOf("") }
    var isSameContrasena by remember { mutableStateOf(true) }

    var validUserName by remember { mutableStateOf(true) }
    var validEmailFormat by remember { mutableStateOf(true) }
    var validFirstName by remember { mutableStateOf(true) }
    var validLastName by remember { mutableStateOf(true) }

    val symbolsCharacters = "[@,.()|<>&\"';+=_-]".toRegex()
    val emailFormat = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$".toRegex()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registro", fontSize = 24.sp)
        TextField(
            value = userName,
            onValueChange = {
                userName = it
            },
            label = {
                Text("Nombre de Usuario")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(
                    width = 2.dp,
                    color = if (isUserNameEmpty || !validUserName) Color.Red else Color.Transparent,
                    shape = MaterialTheme.shapes.small
                )
        )
        if (isUserNameEmpty) {
            Text(text = "El nombre de usuario no puede estar vacío",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp))
        }
        if (!validUserName) {
            Text(text = "El nombre de usuario no puede contener simbolos",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp))
        }

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
                .border(
                    width = 2.dp,
                    color = if (isEmailEmpty || !validEmailFormat) Color.Red else Color.Transparent,
                    shape = MaterialTheme.shapes.small
                )
        )
        if (isEmailEmpty) {
            Text(text = "El email no puede estar vacío",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp))
        }
        if (!validEmailFormat) {
            Text(text = "No email es un email valido",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp))
        }

        TextField(
            value = firstName,
            onValueChange = {
                firstName = it
            },
            label = {
                Text("Nombre")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(
                    width = 2.dp,
                    color = if (!validFirstName) Color.Red else Color.Transparent,
                    shape = MaterialTheme.shapes.small
                )
        )
        if (!validFirstName) {
            Text(text = "El nombre no puede contener simbolos",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp))
        }

        TextField(
            value = lastName,
            onValueChange = {
                lastName = it
            },
            label = {
                Text("Apellido")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(
                    width = 2.dp,
                    color = if (!validLastName) Color.Red else Color.Transparent,
                    shape = MaterialTheme.shapes.small
                )
        )
        if (!validLastName) {
            Text(text = "El apellido no puede contener simbolos",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp))
        }

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
                .border(
                    width = 2.dp,
                    color = if (isContrasenaEmpty || !isSameContrasena) Color.Red else Color.Transparent,
                    shape = MaterialTheme.shapes.small
                ),
            visualTransformation = PasswordVisualTransformation()
        )
        if (isContrasenaEmpty) {
            Text(text = "La contraseña no puede estar vacía",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp))
        }

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
                .border(
                    width = 2.dp,
                    color = if (!isSameContrasena) Color.Red else Color.Transparent,
                    shape = MaterialTheme.shapes.small
                ),
            visualTransformation = PasswordVisualTransformation()
        )
        if (!isSameContrasena) {
            Text(text = "Las contraseñas no coinciden",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp))
        }

        Button(onClick = {
            isUserNameEmpty = userName.isEmpty()
            isEmailEmpty = email.isEmpty()
            isContrasenaEmpty = contrasena.isEmpty()
            isSameContrasena = contrasena == repetirContrasena

            validUserName = !symbolsCharacters.containsMatchIn(userName)
            validEmailFormat = emailFormat.containsMatchIn(email)
            validFirstName = !symbolsCharacters.containsMatchIn(firstName)
            validLastName = !symbolsCharacters.containsMatchIn(lastName)

            if (!isUserNameEmpty && !isEmailEmpty &&
                !isContrasenaEmpty && isSameContrasena &&
                validUserName && validEmailFormat &&
                validFirstName && validLastName) {
                //val newUser = User(userName, email, contrasena, firstName, lastName, "")
                //createOrUpdateUser(context, newUser)
                registerUser(email, contrasena, userName, firstName, lastName
                ) { result ->
                    if (result) {
                        Toast.makeText(context, "Registro correcto", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Error en el Registro", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }) {
            Text(text = "Registrarse")
        }
    }

}