package com.example.talkcompanion.data.model

open class User {
    open var userName: String = ""
    open var email: String = ""
    open var password: String = ""
    open var firstName: String = ""
    open var lastName: String = ""
    open var profilePicture: String = ""

    constructor(userName: String, email: String, password: String, firstName: String, lastName: String, profilePicture: String){
        this.userName = userName
        this.email = email
        this.password = password
        this.firstName = firstName
        this.lastName = lastName
        this.profilePicture = profilePicture
    }

    constructor()
}
