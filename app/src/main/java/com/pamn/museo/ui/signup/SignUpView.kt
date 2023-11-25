package com.pamn.museo.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.pamn.museo.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.pamn.museo.model.AppScreens

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateTo: (AppScreens) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        SignUp(
            Modifier.align(Alignment.TopCenter),
            viewModel,
            navigateTo = { navigateTo(it) }
        )
    }
}

@Composable
fun SignUp(
    modifier: Modifier,
    viewModel: SignUpViewModel,
    navigateTo: (AppScreens) -> Unit
) {
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val confirmPassword: String by viewModel.confirmPassword.observeAsState(initial = "")
    val signUpEnabled: Boolean by viewModel.signUpEnabled.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState())
        ) {
            ImageLogo(Modifier.align(Alignment.CenterHorizontally))
            Spacer (modifier = Modifier.padding(16.dp))
            EmailField(email) { viewModel.onEmailChanged(it) }
            Spacer(modifier = Modifier.padding(8.dp))
            PasswordField(password) { viewModel.onPasswordChanged(it) }
            Spacer(modifier = Modifier.padding(8.dp))
            ConfirmPasswordField(confirmPassword) { viewModel.onConfirmPasswordChanged(it) }
            Spacer(modifier = Modifier.padding(16.dp))
            SignUpButton(signUpEnabled) {
                viewModel.onSignUpSelected(navigateTo = { navigateTo(it) })
            }
        }
    }
}

@Composable
fun SignUpButton(signUpEnabled: Boolean, onSignUpSelected: () -> Unit) {
    Button(
        onClick = { onSignUpSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF253b6e),
            disabledBackgroundColor = Color(0xFF3D62B6),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        enabled = signUpEnabled
    ) {
        Text(text = "Registrarse")
    }
}


@Composable
fun PasswordField(confirmPassword: String, onTextFieldChange: (String) -> Unit) {

    var passwordVisibility by remember { mutableStateOf(false) }

    TextField(
        value = confirmPassword,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Confirmar Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = "boton Ver/Ocultar Contraseña")
            }
        },
    )
}


@Composable
fun ConfirmPasswordField(confirmPassword: String, onTextFieldChange: (String) -> Unit) {

    var passwordVisibility by remember { mutableStateOf(false) }

    TextField(
        value = confirmPassword,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Confirmar Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = "boton Ver/Ocultar Contraseña")
            }
        },
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
        modifier = modifier.size(300.dp)
    )
}

