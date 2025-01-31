package com.example.medidordeimc.db.fb

import com.example.medidordeimc.MainViewModel
import com.example.medidordeimc.model.IMC
import com.example.medidordeimc.model.User
import com.example.medidordeimc.model.UserC
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore

class FBDatabase {
    interface Listener {
        fun onUserLoaded(user: User)
        fun onUserCAdded(user: UserC)
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
            citiesListReg = refCurrUser.collection("userc")
                .addSnapshotListener { snapshots, ex ->
                    if (ex != null) return@addSnapshotListener
                    snapshots?.documentChanges?.forEach { change ->
                        val fbUserC = change.document.toObject(FbUserC::class.java)
                        if (change.type == DocumentChange.Type.ADDED) {
                            listener?.onUserCAdded(fbUserC.toUserC())
                        } else if (change.type == DocumentChange.Type.REMOVED) {
                            listener?.onUserCAdded(fbUserC.toUserC())
                        }
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
    fun registerc(user: UserC) {
        if (auth.currentUser == null)
            throw RuntimeException("User not logged in!")
        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid + "").set(user.toFBUserC());
    }
    fun add(user: UserC) {
        if (auth.currentUser == null)
            throw RuntimeException("User not logged in!")
        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid).collection("usersc")
            .document(user.name).set(user.toFBUserC())
    }
    fun addImc(imc: IMC) {
        if (auth.currentUser == null)
            throw RuntimeException("User not logged in!")
        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid).collection("usersimc")
            .document(imc.imc.toString()).set(imc.toFBImc())
    }
}