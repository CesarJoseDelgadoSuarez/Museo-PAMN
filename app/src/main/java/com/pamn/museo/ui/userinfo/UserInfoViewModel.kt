package com.pamn.museo.ui.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
        // Obtener el usuario actualmente autenticado
        val currentUser: FirebaseUser = authService.getCurrentUser()

        // Si el usuario está autenticado, obtener sus datos de Firestore
        currentUser.let { user ->
            viewModelScope.launch {
                // Supongamos que el documento del usuario en Firestore se guarda con el UID como ID del documento
                val documentId = user.uid

                // Obtener datos del usuario desde Firestore
                val result = firestoreService.getData("users",documentId)

                when (result) {
                    is FirestoreResult.Success -> {
                        _userData.value = result.data
                    }
                    is FirestoreResult.Error -> {
                        // Manejar el error según sea necesario
                    }
                }
            }
        }
    }
}
