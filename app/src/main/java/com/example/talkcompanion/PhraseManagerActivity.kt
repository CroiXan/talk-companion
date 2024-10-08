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
import androidx.lifecycle.ViewModelProvider
import com.example.talkcompanion.common.components.TopBarComponent
import com.example.talkcompanion.data.model.UserPhraseViewModel
import com.example.talkcompanion.feature.phrase.components.PhraseManagerScreen
import com.example.talkcompanion.feature.phrase.functions.getFirebasePhraseListByUserName
import com.example.talkcompanion.ui.theme.TalkCompanionTheme

class PhraseManagerActivity : ComponentActivity() {
    private lateinit var userPhrases: UserPhraseViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPhrases = ViewModelProvider(this).get(UserPhraseViewModel::class.java)

        getFirebasePhraseListByUserName(){ result ->
            userPhrases.updatePhraseList(result)
        }

        enableEdgeToEdge()
        setContent {
            TalkCompanionTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = { TopBarComponent(
                        showMenu = true,
                        showBack = true,
                        context = this,
                        scrollBehavior = scrollBehavior, onArrowBack = { finish() }) }) { innerPadding ->

                    PhraseManagerScreen(this, innerPadding, userPhrases)

                }
            }
        }
    }
}