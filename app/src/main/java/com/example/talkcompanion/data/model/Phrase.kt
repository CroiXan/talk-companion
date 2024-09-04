package com.example.talkcompanion.data.model

class Phrase {
    var id: Int = 0
    var userName: String = ""
    var phrase: String = ""
    var orderNumer: Int = 0

    constructor(id:Int, userName: String, phrase: String, orderNumber: Int){
        this.id = id
        this.userName = userName
        this.phrase = phrase
        this.orderNumer = orderNumber
    }
}