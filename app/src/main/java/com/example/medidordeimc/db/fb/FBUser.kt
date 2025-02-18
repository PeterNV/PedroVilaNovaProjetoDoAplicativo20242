package com.example.medidordeimc.db.fb

import com.example.medidordeimc.model.User

class FBUser {
    fun toUser() = User(name!!, email!!,date!!, altura!!, sexo!!)

    var name : String ? = null
    var email : String ? = null
    var date : String ? = null
    var altura : Float ? = null
    var sexo : String ? = null
}


fun User.toFBUser() : FBUser {
    val fbUser = FBUser()
    fbUser.name = this.name
    fbUser.email = this.email
    fbUser.date = this.date
    fbUser.altura = this.altura
    fbUser.sexo = this.sexo
    return fbUser
}