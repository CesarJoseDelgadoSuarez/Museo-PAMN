package com.pamn.museo.ui.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamn.museo.data.AuthService
import com.pamn.museo.model.AppScreens
import com.pamn.museo.model.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _signUpEnabled = MutableLiveData<Boolean>()
    val signUpEnabled: LiveData<Boolean> = _signUpEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _credentialsError = MutableLiveData<Boolean>()
    val credentialsError: LiveData<Boolean> = _credentialsError

    fun onEmailChanged(email: String) {
        _email.value = email
        updateSignUpButtonState()
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        updateSignUpButtonState()
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
        updateSignUpButtonState()
    }

    private fun updateSignUpButtonState() {
        _signUpEnabled.value =
            isValidEmail(_email.value.toString()) && isValidPassword(_password.value.toString()) &&
                    _password.value == _confirmPassword.value
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean =
        password.length >= 6

    fun onSignUpSelected(navigateTo: (AppScreens) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true

            val signUpResult = authService.signUpWithEmailAndPassword(
                _email.value.toString(),
                _password.value.toString()
            )

            when (signUpResult) {
                is LoginResult.Success -> {
                    val user = signUpResult.user
                    navigateTo(AppScreens.Home)
                }
                is LoginResult.Error -> {
                    val errorMessage = signUpResult.message
                    // Mostrar el mensaje de error al usuario
                }
            }
            _isLoading.value = false
        }
    }
}
