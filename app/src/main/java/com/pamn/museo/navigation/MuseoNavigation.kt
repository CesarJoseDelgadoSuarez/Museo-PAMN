@file:OptIn(ExperimentalMaterial3Api::class)

package com.pamn.museo.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pamn.museo.model.AppScreens
import com.pamn.museo.ui.Home.Pantalla1
import com.pamn.museo.ui.login.LoginScreen
import com.pamn.museo.ui.login.LoginViewModel

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

            LoginScreen(LoginViewModel())
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
    val hasNews: Boolean
)
