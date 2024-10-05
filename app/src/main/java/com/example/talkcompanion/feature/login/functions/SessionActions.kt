package com.example.talkcompanion.feature.login.functions

import android.util.Log
import com.example.talkcompanion.data.model.phrase.PhraseEntity
import com.example.talkcompanion.data.model.user.UserEntity
import com.example.talkcompanion.data.model.user.addUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

fun doLogin(email: String, password: String, callback: (result: Boolean) -> Unit){
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
        .addOnCompleteListener { task ->
            callback(task.isSuccessful)
        }
}

fun doLogout() {
    FirebaseAuth.getInstance().signOut()
}

fun isLoggedIn(): Boolean{
    return FirebaseAuth.getInstance().currentUser != null
}

fun getUser(callback: (result: UserEntity) -> Unit){
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    if (userId != null) {
        FirebaseDatabase.getInstance().getReference("Users")
            .orderByChild("id")
            .equalTo(userId)
            .get()
            .addOnCompleteListener  { task ->
                val phraseList = mutableListOf<PhraseEntity>()
                if (task.isSuccessful) {
                    val snapshot = task.result
                    if (snapshot != null && snapshot.exists()) {

                        for (userSnapshot in snapshot.children) {
                            Log.d("adduser","snap:"+userSnapshot.child("id").getValue(String::class.java))
                            val user = UserEntity(
                                (if (userSnapshot.child("id").getValue(String::class.java) != null) userSnapshot.child("id").getValue(String::class.java) else "")!!,
                                userSnapshot.child("userName").getValue(String::class.java),
                                userSnapshot.child("email").getValue(String::class.java),
                                userSnapshot.child("password").getValue(String::class.java),
                                userSnapshot.child("firstName").getValue(String::class.java),
                                userSnapshot.child("lastName").getValue(String::class.java),
                                userSnapshot.child("profilePicture").getValue(String::class.java)
                            )
                            callback(user)
                        }
                    }
                }
            }

    }
}

fun getUserByEmail(email: String,callback: (result: UserEntity) -> Unit){
        FirebaseDatabase.getInstance().getReference("Users")
            .orderByChild("email")
            .equalTo(email)
            .get()
            .addOnCompleteListener  { task ->
                val phraseList = mutableListOf<PhraseEntity>()
                if (task.isSuccessful) {
                    val snapshot = task.result
                    if (snapshot != null && snapshot.exists()) {

                        for (userSnapshot in snapshot.children) {
                            Log.d("adduser","snap:"+userSnapshot.child("id").getValue(String::class.java))
                            val user = UserEntity(
                                (if (userSnapshot.child("id").getValue(String::class.java) != null) userSnapshot.child("id").getValue(String::class.java) else "")!!,
                                userSnapshot.child("userName").getValue(String::class.java),
                                userSnapshot.child("email").getValue(String::class.java),
                                userSnapshot.child("password").getValue(String::class.java),
                                userSnapshot.child("firstName").getValue(String::class.java),
                                userSnapshot.child("lastName").getValue(String::class.java),
                                userSnapshot.child("profilePicture").getValue(String::class.java)
                            )
                            callback(user)
                        }
                    }
                }
            }
}

fun updateUser(user:UserEntity, isSuccess: (isSuccess: Boolean) -> Unit){
    val updates = hashMapOf<String, Any>()
    val fireDatabase = FirebaseDatabase.getInstance().getReference("Users")
    Log.d("update","email: " + user.email)
    fireDatabase
        .orderByChild("email")
        .equalTo(user.email)
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (childSnapshot in task.result.children) {
                    Log.d("update", "snap: " + childSnapshot.key)
                    updates["/${childSnapshot.key}/password"] = user.password!!
                }

                fireDatabase.updateChildren(updates).addOnCompleteListener { task2 ->
                    Log.d("update", "updateTask: " + task2.isSuccessful)
                    if (task2.isSuccessful) {
                        isSuccess(true)
                    } else {
                        isSuccess(false)
                    }
                }
            }else{
                isSuccess(false)
            }
        }
}