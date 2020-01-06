package com.example.phasal.Model

class Post {


    private var postid : String=""
    private var postimage : String=""
    private var publisher : String=""
    private var description : String=""
    private var likes :Int = 0
    private var comment :String=""

    constructor()

    constructor(postid: String,postimage: String,publisher: String,description: String,likes:Int,comment: String)
    {
        this.postid = postid
        this.postimage = postimage
        this.publisher = publisher
        this.description = description
        this.likes = likes
        this.comment = comment


    }
    fun getPostid() : String {return postid}
    fun setPostid(postid: String){this.postid = postid}

    fun getPostimage() : String {return postimage}
    fun setPostimage(postimage: String){this.postimage = postimage}

    fun getPublisher() : String {return publisher}
    fun setPublisher(publisher: String){this.publisher = publisher}


    fun getDescription() : String {return description}
    fun setDescription(description: String){this.description = description}

    fun getLikes() : Int {return likes}
    fun setLikes(likes: Int){this.likes = likes}

    fun getComment() : String {return comment}
    fun setComment(comment:  String){this.comment = comment}



}