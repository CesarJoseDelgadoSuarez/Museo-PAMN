package com.pamn.museo.model

sealed class FirestoreResult<out T> {
    data class Success<T>(val data: T ) : FirestoreResult<T>()
    data class Error(val message: String) : FirestoreResult<Nothing>()
}