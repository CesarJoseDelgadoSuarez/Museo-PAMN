package com.pamn.museo.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pamn.museo.data.AuthService
import com.pamn.museo.model.AppScreens
import com.pamn.museo.model.BottomNavigationItem
import com.pamn.museo.ui.home.HomeScreen
import com.pamn.museo.ui.SignIn.SignInScreen
import com.pamn.museo.ui.expoelement.ExpoElementScreen
import com.pamn.museo.ui.menuUser.MenuUserScreen
import com.pamn.museo.ui.signup.SignUpScreen
import com.pamn.museo.ui.userlogic.UserLogicScreen
import com.pamn.museo.ui.qr.QrScreen
import com.pamn.museo.ui.userinfo.UserInfoScreen

@ExperimentalMaterial3Api
@Composable
fun MuseoNavigation(authService: AuthService) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = AppScreens.Home.route,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "QrScan",
            selectedIcon = Icons.Filled.QrCodeScanner,
            unselectedIcon = Icons.Outlined.QrCodeScanner,
            route = AppScreens.QrCodeScanner.route,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Login",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = AppScreens.UserLogic.route,
            hasNews = false,
        )
    )

    var selectedIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                selectedIndex = selectedIndex,
                items = items,
                onItemSelected = { index ->
                    selectedIndex = index
                    navController.navigate(items[index].route)
                },
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.Home.route
        ) {
            composable(route = AppScreens.Home.route) {
                HomeScreen()
            }
            composable(route = AppScreens.ExpoElement.route) { backStackEntry ->
                ExpoElementScreen(
                    id = backStackEntry.arguments?.getString("id").orEmpty()
                )
            }
            composable(route = AppScreens.UserLogic.route) {
                UserLogicScreen(){
                    navController.navigate(it.route)
                }
            }
            composable(route = AppScreens.QrCodeScanner.route) {
                QrScreen(){
                    navController.navigate(it)
                }
            }
            composable(route = AppScreens.SignIn.route) {
                SignInScreen(navigateTo = { screen ->
                    val newIndex = items.indexOfFirst { it.route == screen.route }
                    if (newIndex != -1) {
                        selectedIndex = newIndex
                    }
                    navController.navigate(screen.route)
                })
            }
            composable(route = AppScreens.SignUp.route) {
                SignUpScreen(navigateTo = { screen ->
                    val newIndex = items.indexOfFirst { it.route == screen.route }
                    if (newIndex != -1) {
                        selectedIndex = newIndex
                    } else {
                        Log.w("Navegacion!!", "Ruta no reconocida: ${screen.route}")
                    }
                    navController.navigate(screen.route)

                })
            }
            composable(route = AppScreens.UserInfo.route) {
                UserInfoScreen(){
                    navController.navigate(it.route)
                }
            }
        }
    }
}
