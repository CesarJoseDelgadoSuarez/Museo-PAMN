package com.pamn.museo.model

import com.google.firebase.Timestamp

data class UserData(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: Timestamp?
){
    // Constructor sin argumentos necesario para la deserialización de Firestore
    constructor() : this("", "", "", "", null)
}

