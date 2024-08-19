package com.example.talkcompanion

import android.content.Intent
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
import com.example.talkcompanion.common.components.DashboardMockScreen
import com.example.talkcompanion.common.components.TopBarComponent
import com.example.talkcompanion.feature.login.functions.isLoggedIn
import com.example.talkcompanion.ui.theme.TalkCompanionTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        checkSession()
        setContent {
            TalkCompanionTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                Scaffold(modifier = Modifier.fillMaxSize(),
                        topBar = { TopBarComponent(true,
                            showBack = false,
                            context = this,
                            scrollBehavior = scrollBehavior, onArrowBack = { finish() }) }) { innerPadding ->

                    DashboardMockScreen(innerPadding)

                }
            }
        }
    }

    private fun checkSession(){
        if(!isLoggedIn(this)){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}


