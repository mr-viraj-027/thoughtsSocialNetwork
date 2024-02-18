package com.example.thoughts_socialnetwork.models

class AuthUser {
    var image:String?=null
    var username:String?=null
    var email:String?=null
    var bio:String?=null
    var password:String?=null

    constructor()
    constructor(
        image: String?,
        username: String?,
        email: String?,
        bio: String?,
        password: String?
    ) {
        this.image = image
        this.username = username
        this.email = email
        this.bio = bio
        this.password = password
    }
    constructor(
        username: String?,
        email: String?,
        bio: String?,
        password: String?
    ) {
        this.username = username
        this.email = email
        this.bio = bio
        this.password = password
    }

    constructor(email: String?,password: String?) {

        this.email = email
        this.password = password
    }


}