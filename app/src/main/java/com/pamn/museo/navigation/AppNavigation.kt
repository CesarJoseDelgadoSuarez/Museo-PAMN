@file:OptIn(ExperimentalMaterial3Api::class)

package com.pamn.museo.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pamn.museo.views.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    BottomNavBar(navController = navController)
    NavHost(
        navController = navController,
        startDestination = AppScreens.Pantalla1.route
    ){
        composable(route = AppScreens.Pantalla1.route){
            Pantalla1()
        }
        composable(route = AppScreens.Pantalla2.route){
            Pantalla2()
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
