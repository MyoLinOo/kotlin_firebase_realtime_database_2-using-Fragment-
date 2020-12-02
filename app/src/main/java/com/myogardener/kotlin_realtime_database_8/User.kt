package com.myogardener.kotlin_realtime_database_8

class User {
    var key:String?= ""
    var name:String? =""
    var age:String?= ""
    var position:String? =""

    constructor(){}

    constructor(key:String,name:String?,age:String?,position:String?){
        this.key = key
        this.name = name
        this.age = age
        this.position = position
    }
}