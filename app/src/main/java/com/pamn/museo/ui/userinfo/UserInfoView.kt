package com.pamn.museo.ui.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pamn.museo.R
import com.pamn.museo.model.UserData
import com.pamn.museo.ui.theme.MuseoTheme

@Composable
fun UserInfoScreen(
    viewModel: UserInfoViewModel = hiltViewModel(),
) {
    val userData by viewModel.userData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (userData != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.welcome_message,userData!!.firstName, userData!!.lastName ),
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Name: ${userData!!.firstName}", style = MaterialTheme.typography.body1)
                Text(text = "Lastname: ${userData!!.lastName}", style = MaterialTheme.typography.body1)
                Text(text = "Email: ${userData!!.email}", style = MaterialTheme.typography.body1)
            }
        } else {
            // Puedes manejar el caso en el que los datos del usuario no estén disponibles
            Text(text = "Cargando datos...")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewUserInfoView() {
    MuseoTheme {
        UserInfoScreen()
    }
}
