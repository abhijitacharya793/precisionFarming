package com.ultimategroup.phasal.Model

class Disease
{
    var id:Int? = null
    var name:String? = null
    var des:String? = null
    var type:String? = null
    var diagnosis:String? = null
    var causes:String? = null
    var prevention:String? = null
    var treatment:String? = null
    var stage:String? = null

    constructor(id:Int, name:String, des:String, type:String, diagnosis:String, causes:String, prevention:String, treatment:String, stage:String)
    {
        this.id = id
        this.name = name
        this.des = des
        this.type = type
        this.diagnosis = diagnosis
        this.causes = causes
        this.prevention = prevention
        this.treatment = treatment
        this.stage = stage

    }
}