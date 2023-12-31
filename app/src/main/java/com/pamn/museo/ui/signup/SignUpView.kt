@file:OptIn(ExperimentalMaterial3Api::class)

package com.pamn.museo.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pamn.museo.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Timestamp
import com.pamn.museo.model.AppScreens
import java.time.Instant
import java.time.ZoneId

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
            viewModel,
            navigateTo = { navigateTo(it) }
        )
    }
}

@Composable
fun SignUp(
    viewModel: SignUpViewModel,
    navigateTo: (AppScreens) -> Unit
) {
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val confirmPassword: String by viewModel.confirmPassword.observeAsState(initial = "")
    val name: String by viewModel.name.observeAsState(initial = "")
    val lastName: String by viewModel.lastName.observeAsState(initial = "")
    val signUpEnabled: Boolean by viewModel.signUpEnabled.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)



    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {

            ImageLogo(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            NameFields(
                name,
                lastName,
                onNameChanged = { viewModel.onNameChanged(it) },
                onLastNameChanged = { viewModel.onLastNameChanged(it) })
            Spacer(modifier = Modifier.padding(8.dp))
            EmailField(email) { viewModel.onEmailChanged(it) }
            Spacer(modifier = Modifier.padding(8.dp))
            SignUpPasswordField(password, "Contraseña") { viewModel.onPasswordChanged(it) }
            Spacer(modifier = Modifier.padding(8.dp))
            SignUpPasswordField(
                confirmPassword,
                "Confirmar Contraseña"
            ) { viewModel.onConfirmPasswordChanged(it) }
            Spacer(modifier = Modifier.padding(16.dp))

            MyDatePicker(viewModel)

            Spacer(modifier = Modifier.padding(16.dp))
            SignUpButton(signUpEnabled) {
                viewModel.onSignUpSelected(navigateTo = { navigateTo(it) })
            }
        }


    }
}

@Composable
fun MyDatePicker(viewModel: SignUpViewModel) {
    val state = rememberDatePickerState()

    var showDialog by remember { mutableStateOf(false) }

    Button(onClick = { showDialog = true }) {

        Text(text = "Selecciona una Fecha")
    }
    state.selectedDateMillis?.let { millis ->
        val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC")).toLocalDate()
        Text(text = "FechaSeleccionada: ${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}")
        val timeStamp = Timestamp(millis / 1000, 0)
        viewModel.onDateOfBirthSelected(timeStamp)
    }
    if (showDialog){
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = { showDialog = false }, shape = CircleShape) {
                    Text(text = "Confirmar", fontSize = 15.sp)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text(text = "Cancelar")
                }
            },
        ) {
            DatePicker(state = state)
        }
    }


}

@Composable
fun NameFields(
    name: String,
    lastName: String,
    onNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SignUpField(
            modifier = Modifier.weight(1f),
            value = name,
            placeholder = "Nombre"
        ) { onNameChanged(it) }

        Spacer(modifier = Modifier.width(8.dp))

        SignUpField(
            modifier = Modifier.weight(1f),
            value = lastName,
            placeholder = "Apellido"
        ) { onLastNameChanged(it) }
    }
}


@Composable
fun SignUpField(
    modifier: Modifier,
    value: String,
    placeholder: String,
    onTextFieldChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = { onTextFieldChange(it) },
        modifier = modifier,
        placeholder = { Text(text = placeholder) },
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
fun SignUpPasswordField(value: String, placeholder: String, onValueChange: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = placeholder) },
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
            val image =
                if (passwordVisibility) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
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

