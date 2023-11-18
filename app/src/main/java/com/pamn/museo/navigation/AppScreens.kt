package com.pamn.museo.navigation

sealed class AppScreens(val route: String){
    object Pantalla1: AppScreens("pantalla1")
    object Pantalla2: AppScreens("pantalla2")
}
