package com.pamn.museo.model

import com.google.firebase.Timestamp

data class ExpoElement (
    val artista: String,
    val descripcion: String,
    val añoCreacion: String,
    val titulo: String,
    val imagen: String,
    val tipo: String,
    ){
    constructor() : this("", "", "", "", "","")
}