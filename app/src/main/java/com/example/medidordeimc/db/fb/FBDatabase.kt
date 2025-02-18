package com.example.medidordeimc.db.fb

import com.example.medidordeimc.MainViewModel
import com.example.medidordeimc.model.IMC
import com.example.medidordeimc.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore

class FBDatabase {
    interface Listener {
        fun onUserLoaded(user: User)

        fun onImcAdded(imc: IMC)
    }
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private var citiesListReg: ListenerRegistration? = null
    private var listener : Listener? = null
    init {
        auth.addAuthStateListener { auth ->
            if (auth.currentUser == null) {
                citiesListReg?.remove()
                return@addAuthStateListener
            }
            val refCurrUser = db.collection("users")
                .document(auth.currentUser!!.uid)
            refCurrUser.get().addOnSuccessListener {
                it.toObject(FBUser::class.java)?.let { user ->
                    listener?.onUserLoaded(user.toUser())
                }
            }
            val refCurrImc = db.collection("usersimc")
                .document(auth.currentUser!!.uid)
            refCurrImc.get().addOnSuccessListener {
                it.toObject(FBImc::class.java)?.let { imc ->
                    listener?.onImcAdded((imc.toImc()))
                }
            }
        }

    }
    fun setListener(listener: MainViewModel? = null) {
        this.listener = listener
    }

    fun register(user: User) {
        if (auth.currentUser == null)
            throw RuntimeException("User not logged in!")
        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid + "").set(user.toFBUser());
    }

    fun addImc(imc: IMC) {
        if (auth.currentUser == null)
            throw RuntimeException("User not logged in!")
        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid).collection("usersimc")
            .document(imc.imc.toString()).set(imc.toFBImc())

    }

}