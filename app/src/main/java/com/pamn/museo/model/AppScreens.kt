package com.pamn.museo.model

sealed class AppScreens(val route: String){
    object Home: AppScreens("home")
    object SignIn: AppScreens("login")
}
