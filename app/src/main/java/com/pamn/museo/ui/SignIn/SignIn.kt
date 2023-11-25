package com.pamn.museo.ui.SignIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pamn.museo.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.pamn.museo.model.AppScreens

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToHomeOnSuccess: (AppScreens) -> Unit
){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ){
        SignIn(Modifier.align(Alignment.TopCenter), viewModel, navigateToHomeOnSuccess = {navigateToHomeOnSuccess(it)})
    }
}

@Composable
fun SignIn(
    modifier: Modifier,
    viewModel: SignInViewModel,
    navigateToHomeOnSuccess: (AppScreens) -> Unit
) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnabled: Boolean by viewModel.loginEnabled.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val credentialsError: Boolean by viewModel.credentialsError.observeAsState(initial = false)


    if (isLoading){
    Box(modifier = Modifier.fillMaxSize()){
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
    }else{
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            ImageLogo(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            EmailField(email){viewModel.onLoginChanged(it,password)}
            Spacer(modifier = Modifier.padding(8.dp))
            PasswordField(password){viewModel.onLoginChanged(email,it)}
            Spacer(modifier = Modifier.padding(8.dp))
            if (credentialsError){
                CredencialesIncorrectas(Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.padding(8.dp))
            }
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnabled){
                viewModel.onLoginSelected(navigateToHomeOnSuccess = { navigateToHomeOnSuccess(it) })
            }
        }
    }
}

@Composable
fun CredencialesIncorrectas(modifier: Modifier) {
    Text(
        text = "El correo o la contraseña son incorrectos",
        modifier = modifier,
        color = Color.Red
    )
}

@Composable
fun LoginButton(loginEnabled: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF253b6e),
            disabledBackgroundColor = Color(0xFF3D62B6),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        enabled = loginEnabled
    ) {
        Text(text = "Iniciar Sesión")
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Olvidaste la contraseña?",
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChange: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun EmailField(email: String, onTextFieldChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
@Composable
fun ImageLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo_museo),
        contentDescription = "Logo de la App",
        modifier = modifier
    )
}

