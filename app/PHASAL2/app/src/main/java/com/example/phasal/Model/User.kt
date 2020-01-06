package com.example.phasal.Model

class User {


    private var username : String=""
    private var fullname : String=""
    private var bio : String=""
    private var uid : String=""
    private var image : String=""

    constructor()

    constructor(username : String,fullname : String,bio : String,uid : String,image : String)
    {
        this.username = username
        this.fullname = fullname
        this.bio = bio
        this.uid = uid
        this.image = image

    }
    fun getUsername() : String {return username}
    fun setUsername(username: String){this.username = username}


    fun getfullname() : String {return fullname}
    fun setfullname(fullname: String){this.fullname = fullname}

    fun getbio() : String {return bio}
    fun setbio(bio: String){this.bio = bio}

    fun getimage() : String {return image}
    fun setimage(image: String){this.image = image}

    fun getUid() : String {return uid}
    fun setUid(uid: String){this.uid = uid}

}