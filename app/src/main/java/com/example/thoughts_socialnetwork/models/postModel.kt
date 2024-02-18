package com.example.thoughts_socialnetwork.models

class postModel {
    var posturl :String =""
    var caption :String =""
    var uid :String=""
    var time :String=""
    constructor()
    constructor(posturl: String, caption: String) {
        this.posturl = posturl
        this.caption = caption
    }

    constructor(posturl: String, caption: String,uid: String, time: String) {
        this.posturl = posturl
        this.caption = caption
        this.uid = uid
        this.time = time
    }


}