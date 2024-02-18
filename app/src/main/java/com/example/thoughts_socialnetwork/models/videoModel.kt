package com.example.thoughts_socialnetwork.models

class videoModel {
    var videourl :String =""
    var caption :String =""
    var profileLink:String?=null
    constructor()

    constructor(videourl: String, caption: String) {
        this.videourl = videourl
        this.caption = caption
    }

    constructor(videourl: String, caption: String, profileLink: String) {
        this.videourl = videourl
        this.caption = caption
        this.profileLink = profileLink
    }


}