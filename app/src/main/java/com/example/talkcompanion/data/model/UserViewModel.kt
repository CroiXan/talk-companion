package com.example.talkcompanion.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.talkcompanion.data.model.phrase.PhraseEntity
import com.example.talkcompanion.data.model.user.UserEntity

class UserViewModel: ViewModel() {
    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity> = _user

    fun updateUser(currentUser: UserEntity){
        _user.value = currentUser
    }
}