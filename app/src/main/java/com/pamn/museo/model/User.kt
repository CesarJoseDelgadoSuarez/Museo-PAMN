package com.pamn.museo.model

import java.util.Date

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: Date
)

