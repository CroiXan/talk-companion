package com.example.talkcompanion.data.model

class User {
    var userName: String = ""
    var email: String = ""
    var password: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var profilePicture: String = ""

    constructor(userName: String, email: String, password: String, firstName: String, lastName: String, profilePicture: String){
        this.userName = userName
        this.email = email
        this.password = password
        this.firstName = firstName
        this.lastName = lastName
        this.profilePicture = profilePicture
    }
}
