package com.pamn.museo.data

import com.google.firebase.Timestamp
import com.pamn.museo.model.FirestoreResult
import com.pamn.museo.model.LoginResult
import com.pamn.museo.model.UserData
import javax.inject.Inject

class UserService @Inject constructor(
    private val firestoreService: FirestoreService<UserData>,
    private val authService: AuthService
){
    suspend fun SignUpWithEmailAndPassword(email: String, password: String, firstName: String, lastName: String, dateOfBirth: Timestamp ): LoginResult {
        val loginResult = authService.signUpWithEmailAndPassword(email, password)
        when(loginResult) {
            is LoginResult.Success -> {
                val userData = UserData(
                    loginResult.user.uid,
                    firstName,
                    lastName,
                    email,
                    dateOfBirth
                )
                val firestoreResult = firestoreService.insertDataWithDocumentID("users", loginResult.user.uid, userData)
                when(firestoreResult){
                    is FirestoreResult.Success -> {
                        return loginResult
                    }
                    is FirestoreResult.Error -> {
                        authService.deleteUser()
                        return LoginResult.Error(firestoreResult.message)
                    }
                }
            }
            is LoginResult.Error ->{
                return loginResult
            }
        }
    }
    suspend fun getUserData(userId: String): FirestoreResult<UserData> {
        return firestoreService.getData("users", userId)
    }
    suspend fun insertUserData(userId: String, userData: UserData): FirestoreResult<String> {
        return firestoreService.insertDataWithDocumentID("users", userId, userData)
    }

    fun getCurrentUser() = authService.getCurrentUser()

    fun signOut() = authService.signOut()
}