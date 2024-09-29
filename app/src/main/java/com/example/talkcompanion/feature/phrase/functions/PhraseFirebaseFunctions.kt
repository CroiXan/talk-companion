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
            .addOnSuccessListener { dataSnapshot ->
                val phraseList = mutableListOf<PhraseEntity>()

                if (dataSnapshot.exists()){
                    for (phraseSnapshot in dataSnapshot.children){
                        phraseList.add(PhraseEntity(
                            (if (phraseSnapshot.child("id").getValue(Int::class.java) != null) phraseSnapshot.child("id").getValue(Int::class.java) else 0)!!,
                            phraseSnapshot.child("userId").getValue(String::class.java),
                            phraseSnapshot.child("userName").getValue(String::class.java),
                            phraseSnapshot.child("phrase").getValue(String::class.java),
                            phraseSnapshot.child("orderNumber").getValue(Int::class.java)
                        ))
                    }
                }
                val result = ArrayList(phraseList)
                callback(result)
            }
            .addOnFailureListener { e ->
                callback(ArrayList<PhraseEntity>())
            }

    }else{
        callback(ArrayList<PhraseEntity>())
    }
}

fun getPhraseMaxId(callback: (result: Int) -> Unit){
    FirebaseDatabase.getInstance().getReference("Phrases")
        .orderByChild("id").limitToLast(1)
        .get()
        .addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(PhraseEntity::class.java)
                    if (user != null) {
                        callback(user.id)
                    }else{
                        callback(0)
                    }
                }

            } else {
                callback(0)
            }
        }.addOnFailureListener { exception ->
            callback(0)
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

fun updateFirebaseUserPhrases(userPhrases: List<PhraseEntity>){
    val updates = hashMapOf<String, Any>()

    for (currentPhrase in userPhrases){
        if (currentPhrase.phrase!!.isNotEmpty()){
            updates["/${currentPhrase.id}/phrase"] = currentPhrase.phrase!!
        }
        updates["/${currentPhrase.id}/orderNumber"] = currentPhrase.orderNumber!!
    }

}

/*fun deleteFirebasePhraseById(context: Context, phraseId: Int, userPhrases: List<PhraseEntity>): List<Phrase>{
    updatePhrasePositionsBeforeDelete(context,phraseId)
    val phraseList = getPhraseList(context)
    val resultList = phraseList.filter { it.id != phraseId }.toTypedArray()
    savePhraseListMock(context, Gson().toJson(resultList))
    return getPhraseListByUserName(context)
}

private fun updatePhrasePositionsBeforeDelete(phraseId: Int, userPhrases: List<PhraseEntity>){
    val indexOfPhraseId = userPhrases.indexOfFirst { it.id == phraseId }

    for (currentPhrase in userPhrases){
        if (userPhrases[indexOfPhraseId].orderNumber!! < currentPhrase.orderNumber!!){
            currentPhrase.orderNumber = currentPhrase.orderNumber!! - 1
        }
    }
    updateFirebaseUserPhrases(userPhrases)
}*/