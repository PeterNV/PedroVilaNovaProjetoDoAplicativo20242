package com.example.medidordeimc.db.fb

import com.example.medidordeimc.model.IMC


class FBImc {
    fun toImc() = IMC(imc!!,  datet!!, peso!!, fotoname!!)
    var imc: Float ? = null
    var datet: String ? = null
    var peso: Float? = null
    var fotoname: String? = null
}

fun IMC.toFBImc() : FBImc {
    val fbImc = FBImc()
    fbImc.imc = this.imc
    fbImc.datet = this.datet
    fbImc.peso = this.peso
    fbImc.fotoname = this.fotoname
    return fbImc
}