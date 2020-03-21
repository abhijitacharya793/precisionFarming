package com.ultimategroup.phasal.Model

class Season
{
    var id:Int? = null
    var name:String? = null
    var start_month:String? = null
    var end_month:String? = null
    constructor(id:Int, name:String, start_month:String, end_month:String)
    {
        this.id = id
        this.name = name
        this.start_month = start_month
        this.end_month = end_month
    }
}