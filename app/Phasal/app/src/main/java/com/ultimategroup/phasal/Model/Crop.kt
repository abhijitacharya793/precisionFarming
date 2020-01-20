package com.ultimategroup.phasal.Model

class Crop
{
    var id:String? = null
    var name:String? = null
    var des:String? = null
    var season:Int? = null
    constructor(id:String, name:String, des:String, season: Int)
    {
        this.id = id
        this.name = name
        this.des = des
        this.season = season
    }
}