package com.pamn.museo.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthService @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    suspend fun loginWithEmailAndPassword(email: String, password: String): FirebaseUser? {
        return firebaseAuth.signInWithEmailAndPassword(email,password).await().user
    }

}