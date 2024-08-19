package com.example.talkcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import com.example.talkcompanion.common.components.TopBarComponent
import com.example.talkcompanion.feature.usermanagement.components.RegistroScreen
import com.example.talkcompanion.ui.theme.TalkCompanionTheme

class RegistroActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TalkCompanionTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = { TopBarComponent(
                        showMenu = false,
                        showBack = false,
                        context = this,
                        scrollBehavior = scrollBehavior, onArrowBack = { finish() }) }) { innerPadding ->
                    RegistroScreen(innerPadding,this)
                }
            }
        }
    }
}
