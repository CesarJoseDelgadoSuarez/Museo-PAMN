@file:OptIn(ExperimentalMaterial3Api::class)

package com.pamn.museo.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pamn.museo.model.AppScreens
import com.pamn.museo.ui.home.Pantalla1
import com.pamn.museo.ui.login.LoginScreen

@Composable
fun MuseoNavigation(){
    val navController = rememberNavController()

    BottomNavBar(navController = navController)
    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.route
    ){
        composable(route = AppScreens.Home.route){
            Pantalla1()
        }
        composable(route = AppScreens.Login.route){
            LoginScreen()
        }
    }
}


