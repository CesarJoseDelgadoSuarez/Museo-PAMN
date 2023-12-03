package com.pamn.museo.ui.userlogic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pamn.museo.model.AppScreens

@Composable
fun UserLogicScreen(
    viewModel: UserLogicViewModel = hiltViewModel(),
    navigateTo: (AppScreens) -> Unit
) {
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = true)
    val screen: AppScreens by viewModel.screen.observeAsState(initial = AppScreens.SignIn)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        if (!isLoading) {
            navigateTo(screen)
        }
    }
}