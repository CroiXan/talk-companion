package com.example.talkcompanion.feature.phrase.functions

import android.content.Context
import android.util.Log
import com.example.talkcompanion.data.model.phrase.PhraseEntity
import com.example.talkcompanion.feature.usermanagement.functions.savePhraseListMock
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson

fun getFirebasePhraseListByUserName(callback: (result: List<PhraseEntity>) -> Unit) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    Log.d("firebaseid", userId+"")
    if (userId != null) {
        FirebaseDatabase.getInstance().getReference("Phrases")
            .orderByChild("userId")
            .equalTo(userId)
            .get()
            .addOnCompleteListener  { task ->
                val phraseList = mutableListOf<PhraseEntity>()
                if (task.isSuccessful) {
                    val snapshot = task.result
                    Log.d("getdatasnapshot", snapshot.exists().toString())
                    if (snapshot != null && snapshot.exists()) {

                        for (phraseSnapshot in snapshot.children) {
                            phraseList.add(
                                PhraseEntity(
                                    (if (phraseSnapshot.child("id").getValue(Int::class.java) != null) phraseSnapshot.child("id").getValue(Int::class.java) else 0)!!,
                                    phraseSnapshot.child("userId").getValue(String::class.java),
                                    phraseSnapshot.child("userName").getValue(String::class.java),
                                    phraseSnapshot.child("phrase").getValue(String::class.java),
                                    phraseSnapshot.child("orderNumber").getValue(Int::class.java)
                                )
                            )
                        }
                    }
                }
                val result = ArrayList(phraseList).sortedBy { it.orderNumber }
                callback(result)
            }

    }else{
        callback(ArrayList<PhraseEntity>())
    }
}

fun getPhraseMaxId(callback: (result: Int) -> Unit){
    FirebaseDatabase.getInstance().getReference("Phrases")
        .orderByChild("id")
        .limitToLast(1)
        .get()
        .addOnCompleteListener  { task ->
            val phraseList = mutableListOf<PhraseEntity>()
            if (task.isSuccessful) {
                val snapshot = task.result
                Log.d("getdatasnapshot", snapshot.exists().toString())
                if (snapshot != null && snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        callback((if (childSnapshot.child("id").getValue(Int::class.java) != null) childSnapshot.child("id").getValue(Int::class.java) else 0)!!)
                    }
                }else{
                    callback(0)
                }
            }else{
                callback(0)
            }

        }
}

fun addFirebasePhraseList(
    newPhrase: String,
    userPhraseList: List<PhraseEntity>,
    callback: (result: List<PhraseEntity>) -> Unit
){
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    getPhraseMaxId() { maxid ->
        Log.d("addelement", userId+" maxid "+maxid)
        var orderNumber = 0
        if (userPhraseList.isNotEmpty()){
            orderNumber = userPhraseList.maxBy { it.orderNumber!! }.orderNumber!!
        }
        val newPhraseItem = PhraseEntity(maxid+1,userId, userId, newPhrase, orderNumber + 1)

        FirebaseDatabase.getInstance().getReference("Phrases")
            .push()
            .setValue(newPhraseItem)
            .addOnCompleteListener { task ->
                Log.d("addelement", "task : " + task)
                if (task.isSuccessful) {
                    callback(userPhraseList + listOf(newPhraseItem))
                } else {
                    callback(userPhraseList)
                }
            }
    }

}

fun updateFirebaseUserPhrases(userPhrases: List<PhraseEntity>, isSuccess: (isSuccess: Boolean) -> Unit){
    val updates = hashMapOf<String, Any>()
    val fireDatabase = FirebaseDatabase.getInstance().getReference("Phrases")

    fireDatabase.get().addOnSuccessListener { snapshot ->
        if (snapshot.exists()) {
            for (childSnapshot in snapshot.children) {
                val userid = (if (childSnapshot.child("id").getValue(Int::class.java) != null) childSnapshot.child("id").getValue(Int::class.java) else 0)!!
                val matchPhrase = userPhrases.find { it.id == userid }
                if (matchPhrase != null) {
                    updates["/${childSnapshot.key}/phrase"] = matchPhrase.phrase!!
                    updates["/${childSnapshot.key}/orderNumber"] = matchPhrase.orderNumber!!
                    Log.d("FirebaseUpdate", "Preparando data para: ${childSnapshot.key} ${matchPhrase.id} ${matchPhrase.phrase} ${matchPhrase.orderNumber}")
                }
            }

            fireDatabase.updateChildren(updates).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseUpdate", "Actualizacion exitosa de updateFirebaseUserPhrases")
                    isSuccess(true)
                } else {
                    Log.e("FirebaseUpdate", "Error en la actualizacion: ${task.exception?.message}")
                    isSuccess(false)
                }
            }
        } else {
            isSuccess(false)
        }
    }.addOnFailureListener { exception ->
        isSuccess(false)
    }

}

fun deleteFirebasePhraseById(phraseId: Int, userPhrases: List<PhraseEntity>, callback: (result: List<PhraseEntity>) -> Unit){
    updatePhrasePositionsBeforeDelete(phraseId,userPhrases)

    FirebaseDatabase.getInstance().getReference("Phrases")
        .orderByChild("id")
        .equalTo(phraseId.toDouble())
        .get()
        .addOnCompleteListener  { task ->
            val phraseList = mutableListOf<PhraseEntity>()
            if (task.isSuccessful) {
                val snapshot = task.result
                Log.d("getdatasnapshot", snapshot.exists().toString())
                if (snapshot != null && snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        childSnapshot.ref.removeValue()
                            .addOnSuccessListener{ value ->
                                getFirebasePhraseListByUserName(){ result ->
                                    callback(result)
                                }
                            }
                            .addOnFailureListener { value ->
                                callback(userPhrases)
                            }
                    }

                }else{
                    callback(userPhrases)
                }
            }else{
                callback(userPhrases)
            }
        }
}

private fun updatePhrasePositionsBeforeDelete(phraseId: Int, userPhrases: List<PhraseEntity>){
    val indexOfPhraseId = userPhrases.indexOfFirst { it.id == phraseId }

    for (currentPhrase in userPhrases){
        if (userPhrases[indexOfPhraseId].orderNumber!! < currentPhrase.orderNumber!!){
            currentPhrase.orderNumber = currentPhrase.orderNumber!! - 1
        }
    }
    updateFirebaseUserPhrases(userPhrases){}
}