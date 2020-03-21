package com.ultimategroup.phasal.Model

class District
{
    var id:Int? = null
    var name:String? = null
    var soil_zone:String? = null
    constructor(id:Int, name:String, soil_zone:String)
    {
        this.id = id
        this.name = name
        this.soil_zone = soil_zone
    }
}