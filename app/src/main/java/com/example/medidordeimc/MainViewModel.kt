package com.example.medidordeimc

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medidordeimc.db.fb.FBDatabase
import com.example.medidordeimc.model.IMC
import com.example.medidordeimc.model.User


class MainViewModel (private val db: FBDatabase): ViewModel(), FBDatabase.Listener {
    init {
        db.setListener(this)
    }


    private val _imc = mutableStateOf<IMC?> (null)
    val imc : IMC?
        get() = _imc.value

    private val _user = mutableStateOf<User?> (null)
    val user : User?
        get() = _user.value

    override fun onUserLoaded(user: User) {
        _user.value = user
    }
    override fun onImcAdded(imc: IMC) {
        _imc.value = imc
    }


}

class MainViewModelFactory(private val db : FBDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
