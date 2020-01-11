package com.example.phasal.Model

class Gallery {
    private var postid : String=""
    private var galleryimage : String=""
    private var publisher : String=""
    private var description:String=""
    private var comment :String= ""


    constructor()

    constructor(postid : String,galleyimage : String,publisher : String,description:String,comment :String)
    {
        this.postid = postid
        this.galleryimage = galleyimage
        this.publisher = publisher
        this.description = description
        this.comment = comment


    }

    fun getPostid() : String {return postid}
    fun setPostid(postid: String){this.postid = postid}

    fun getPostimage() : String {return galleryimage}
    fun setPostimage(galleyimage: String ){this.galleryimage = galleyimage}

    fun getPublisher() : String {return publisher}
    fun setPublisher(publisher: String){this.publisher = publisher}


    fun getDescription() : String {return description}
    fun setDescription(description: String){this.description = description}


    fun getComment() : String {return comment}
    fun setComment(comment:  String){this.comment = comment}

}