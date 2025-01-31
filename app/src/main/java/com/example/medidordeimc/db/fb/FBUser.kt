package com.example.medidordeimc.db.fb

import com.example.medidordeimc.model.User

class FBUser {
    fun toUser() = User(name!!, email!!)

    var name : String ? = null
    var email : String ? = null

}


fun User.toFBUser() : FBUser {
    val fbUser = FBUser()
    fbUser.name = this.name
    fbUser.email = this.email

    return fbUser
}