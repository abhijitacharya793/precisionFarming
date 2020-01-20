package com.ultimategroup.phasal.Model

class SoilZone
{
    var id:Int? = null
    var name:String? = null
    var characteristics:String? = null
    constructor(id:Int, name:String, characteristics:String)
    {
        this.id = id
        this.name = name
        this.characteristics = characteristics
    }
}