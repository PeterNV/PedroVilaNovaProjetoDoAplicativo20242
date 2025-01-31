package com.example.medidordeimc

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medidordeimc.db.fb.FBDatabase
import com.example.medidordeimc.model.IMC
import com.example.medidordeimc.model.User
import com.example.medidordeimc.model.UserC


class MainViewModel (private val db: FBDatabase): ViewModel(), FBDatabase.Listener {
    init {
        db.setListener(this)
    }
    private val _users = mutableStateOf<UserC?> (null)
    val users : UserC?
        get() = _users.value

    private val _imcs = mutableStateOf<IMC?> (null)
    val imcs : IMC?
        get() = _imcs.value

    private val _user = mutableStateOf<User?> (null)
    val user : User?
        get() = _user.value

    override fun onUserLoaded(user: User) {
        _user.value = user
    }
    override fun onImcAdded(imc: IMC) {
        _imcs.value = imcs
    }
    override fun onUserCAdded(user: UserC) {
        _users.value = user
        //_users.add(user)
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
