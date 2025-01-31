package com.example.medidordeimc.db.fb

import com.example.medidordeimc.model.UserC

class FbUserC {
    var name : String ? = null
    var date : String ? = null
    var altura : Float ? = null
    var sexo : String ? = null
    fun toUserC(): UserC {

        return UserC(name!!, date!!, altura!!,sexo!!)
    }
}

fun UserC.toFBUserC() : FbUserC {
    val fbUser = FbUserC()

    fbUser.name = this.name
    fbUser.date = this.date
    fbUser.altura = this.altura
    fbUser.sexo = this.sexo
    return fbUser
}