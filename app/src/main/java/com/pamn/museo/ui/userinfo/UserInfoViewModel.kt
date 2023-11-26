package com.pamn.museo.ui.userinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamn.museo.data.AuthService
import com.pamn.museo.data.FirestoreService
import com.pamn.museo.model.FirestoreResult
import com.pamn.museo.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val authService: AuthService,
    private val firestoreService: FirestoreService<UserData>
) : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val user = authService.getCurrentUser()
        Log.d("UserInfoViewModel", "obtenido user: $user")
        if (user != null) {
            viewModelScope.launch {
                // Utiliza la función de FirestoreService para obtener los datos del usuario
                val result = firestoreService.getData("users",user.uid)
                if (result is FirestoreResult.Success) {
                    _userData.value = result.data
                }
            }
        }
    }
}
