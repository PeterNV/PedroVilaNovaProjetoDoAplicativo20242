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
        fun onImcsAdded(imc: IMC)
        fun onDatesAdded(date: IMC)
    }
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private var imcListReg: ListenerRegistration? = null
    private var imcDateReg: ListenerRegistration? = null
    private var listener : Listener? = null
    init {
        auth.addAuthStateListener { auth ->
            if (auth.currentUser == null) {
                imcListReg?.remove()
                return@addAuthStateListener
            }
            val refCurrUser = db.collection("users")
                .document(auth.currentUser!!.uid)
            refCurrUser.get().addOnSuccessListener {
                it.toObject(FBUser::class.java)?.let { user ->
                    listener?.onUserLoaded(user.toUser())
                }
            }

            imcListReg = refCurrUser.collection("usersimc")
                .addSnapshotListener { snapshots, ex ->
                    if (ex != null) return@addSnapshotListener
                    snapshots?.documentChanges?.forEach { change ->
                        val fbImc = change.document.toObject(FBImc::class.java)
                        if (change.type == DocumentChange.Type.ADDED) {
                            listener?.onImcsAdded(fbImc.toImc())
                        }
                    }
                }
            imcDateReg = refCurrUser.collection("usersimc")
                .addSnapshotListener { snapshots, ex ->
                    if (ex != null) return@addSnapshotListener
                    snapshots?.documentChanges?.forEach { change ->
                        val fbImc = change.document.toObject(FBImc::class.java)
                        if (change.type == DocumentChange.Type.ADDED) {
                            listener?.onImcsAdded(fbImc.toImc())
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

    fun addImc(imc: IMC) {
        if (auth.currentUser == null)
            throw RuntimeException("User not logged in!")
        val uid = auth.currentUser!!.uid
        db.collection("users").document(uid).collection("usersimc")
            .document(imc.imc.toString()).set(imc.toFBImc())

    }
    fun update(imc: IMC) {
        if (auth.currentUser == null) throw RuntimeException("Not logged in!")
        val uid = auth.currentUser!!.uid
        val fbImc = imc.toFBImc()
        val changes = mapOf(
            "imc" to fbImc.imc,
            "datet" to fbImc.datet,
            "peso" to fbImc.peso,
            "fotoname" to fbImc.fotoname,
            "monitored" to fbImc.monitored // Adiciona o estado de monitoramento
        )
        db.collection("users").document(uid)
            .collection("usersimc").document(imc.datet).update(changes)
    }
}