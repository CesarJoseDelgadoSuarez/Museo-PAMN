package com.pamn.museo.model

import com.google.firebase.auth.FirebaseUser

sealed class LoginResult {
    data class Success(val user: FirebaseUser) : LoginResult()
    data class Error(val message: String) : LoginResult()
}