package com.pamn.museo.model

sealed class AppScreens(val route: String){

    object Home: AppScreens("home")
    object SignIn: AppScreens("signin")
    object SignUp: AppScreens("signup")
    object UserInfo: AppScreens("userinfo")
    object UserLogic: AppScreens("userlogic")
    object QrCodeScanner: AppScreens("qrcodescanner")
    object ExpoElement: AppScreens("expoelement/{id}")
}
