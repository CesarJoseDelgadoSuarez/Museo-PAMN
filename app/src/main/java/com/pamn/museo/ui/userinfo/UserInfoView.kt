package com.pamn.museo.ui.userinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pamn.museo.R
import com.pamn.museo.model.AppScreens

@Composable
fun UserInfoScreen(
    viewModel: UserInfoViewModel = hiltViewModel(),
    navigateTo: (AppScreens) -> Unit
) {
    val userData by viewModel.userData.observeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (userData != null) {
            UserAvatar()

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Nombre: ${userData!!.firstName} ${userData!!.lastName}", style = MaterialTheme.typography.body1)
            Text(text = "Email: ${userData!!.email}", style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.height(64.dp))

            CustomButtonForUsers(
                text = "Cerrar sesión",
                onClick = {
                    viewModel.signOut()
                    navigateTo(AppScreens.SignIn)
                },
                backgroundColor = Color.Red
            )
        } else {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun UserAvatar() {
    val userImage = painterResource(id = R.drawable.user_image)
    Image(
        painter = userImage,
        contentDescription = "Imagen de perfil",
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
    )
}

@Composable
fun CustomButtonForUsers(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Text(text = text, color = Color.White)
    }
}