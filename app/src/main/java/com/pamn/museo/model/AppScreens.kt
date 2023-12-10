package com.pamn.museo.model

sealed class AppScreens(val route: String){

    object Home: AppScreens("home")
    object SignIn: AppScreens("signin")
    object SignUp: AppScreens("signup")
    object UserMenu: AppScreens("usermenu")
    object UserLogic: AppScreens("userlogic")
    object QrCodeScanner: AppScreens("qrcodescanner")
    object ExpoElement: AppScreens("expoelement")
}
