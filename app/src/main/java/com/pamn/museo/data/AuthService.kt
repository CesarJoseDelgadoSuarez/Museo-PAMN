package com.pamn.museo.data

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.pamn.museo.model.LoginResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthService @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    suspend fun loginWithEmailAndPassword(email: String, password: String): LoginResult {
        try {
            val user = firebaseAuth.signInWithEmailAndPassword(email, password).await().user
            return LoginResult.Success(user!!)
        } catch (e: FirebaseAuthException) {
            val errorMessage = when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> "Correo electrónico no válido."
                "ERROR_WRONG_PASSWORD" -> "Contraseña incorrecta."
                "ERROR_USER_NOT_FOUND" -> "Usuario no encontrado."
                // Otros códigos de error según sea necesario...
                else -> "Error de inicio de sesión: ${e.message}"
            }
            return LoginResult.Error(errorMessage)
        } catch (e: Exception) {
            val errorMessage = "Error desconocido: ${e.toString()}"
            return LoginResult.Error(errorMessage)
        }
    }

    suspend fun signUpWithEmailAndPassword(email: String, password: String): LoginResult {
        try {
            val user = firebaseAuth.createUserWithEmailAndPassword(email, password).await().user
            return LoginResult.Success(user!!)
        } catch (e: FirebaseAuthException) {
            val errorMessage = when (e.errorCode) {
                "ERROR_WEAK_PASSWORD" -> "Contraseña débil. Debe tener al menos 6 caracteres."
                "ERROR_INVALID_EMAIL" -> "Correo electrónico no válido."
                "ERROR_EMAIL_ALREADY_IN_USE" -> "El correo electrónico ya está en uso."
                // Otros códigos de error según sea necesario...
                else -> "Error de registro: ${e.message}"
            }
            return LoginResult.Error(errorMessage)
        } catch (e: Exception) {
            val errorMessage = "Error desconocido: ${e.toString()}"
            return LoginResult.Error(errorMessage)
        }
    }

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
    fun signOut() = firebaseAuth.signOut()
    fun deleteUser() = firebaseAuth.currentUser!!.delete()
    fun isLoggedIn(): Boolean = firebaseAuth.currentUser != null


}