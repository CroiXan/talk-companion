package com.example.talkcompanion.feature.login.functions

import com.google.firebase.auth.FirebaseAuth

fun doLogin(email: String, password: String, callback: (result: Boolean) -> Unit){
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
        .addOnCompleteListener { task ->
            callback(task.isComplete)
        }
}

fun doLogout() {
    FirebaseAuth.getInstance().signOut()
}

fun isLoggedIn(): Boolean{
    return FirebaseAuth.getInstance().currentUser != null
}