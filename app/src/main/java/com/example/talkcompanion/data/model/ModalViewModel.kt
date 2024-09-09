package com.example.talkcompanion.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModalViewModel: ViewModel() {
    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    private val _showModal = MutableLiveData(false)
    val showModal: LiveData<Boolean> = _showModal

    fun updateMessage(newMessage: String){
        _message.value = newMessage
    }

    fun updateShowModal(showModalState: Boolean){
        _showModal.value = showModalState
    }
}