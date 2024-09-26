package com.example.talkcompanion.feature.usermanagement.functions

import android.util.Log
import com.example.talkcompanion.data.model.user.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

fun registerUser(email: String,
                         password: String,
                         userName: String,
                         firstName: String,
                         lastName: String,
                         callback: (result: Boolean) -> Unit) {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                val userId = FirebaseAuth.getInstance().currentUser?.uid

                Log.d("FireCreateUser","userid : "+userId)

                if (userId != null) {
                    val user = UserEntity(userId, userName, email, "", firstName, lastName, "")
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(userId)
                        .setValue(user)
                        .addOnCompleteListener {
                            callback(true)
                        }
                        .addOnFailureListener { e ->
                            callback(false)
                        }
                }

            } else {
                callback(false)
            }
        }
}