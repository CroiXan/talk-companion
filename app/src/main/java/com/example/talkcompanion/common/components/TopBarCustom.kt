package com.example.talkcompanion.common.components

import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import com.example.talkcompanion.CuentaUsuarioActivity
import com.example.talkcompanion.LoginActivity
import com.example.talkcompanion.MainActivity
import com.example.talkcompanion.feature.login.functions.doLogout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(showMenu: Boolean, showBack: Boolean, context: Context,scrollBehavior: TopAppBarScrollBehavior, onArrowBack: () -> Unit) {
    var expandirMenu by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                "TalkCompanion",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = { onArrowBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        actions = {
            if (showMenu) {
                IconButton(onClick = { expandirMenu = !expandirMenu }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                    DropdownMenu(
                        expanded = expandirMenu,
                        onDismissRequest = { expandirMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Inicio") },
                            onClick = {
                                val intent = Intent(context, MainActivity::class.java)
                                context.startActivity(intent)
                                expandirMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Usuario") },
                            onClick = {
                                val intent = Intent(context, CuentaUsuarioActivity::class.java)
                                context.startActivity(intent)
                                expandirMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cerrar Sesión") },
                            onClick = {
                                doLogout(context)
                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                                expandirMenu = false
                            }
                        )
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}