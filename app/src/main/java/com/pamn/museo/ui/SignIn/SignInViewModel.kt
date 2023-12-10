package com.pamn.museo.ui.SignIn

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
class SignInViewModel @Inject constructor(
    private val authService: AuthService
): ViewModel(){

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnabled = MutableLiveData<Boolean>()
    val loginEnabled: LiveData<Boolean> = _loginEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _credentialsError = MutableLiveData<Boolean>()
    val credentialsError: LiveData<Boolean> = _credentialsError


    fun onLoginChanged(email: String, password:String){
        _email.value = email
        _password.value = password
        _loginEnabled.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(password: String): Boolean = password.length >= 6
    fun onLoginSelected(navigateTo: (AppScreens) -> Unit) {
        viewModelScope.launch {
            _isLoading.value= true

            val loginResult = authService.loginWithEmailAndPassword(_email.value.toString(), _password.value.toString())

            when (loginResult) {
                is LoginResult.Success -> {
                    val user = loginResult.user
                    navigateTo(AppScreens.UserInfo)
                }
                is LoginResult.Error -> {
                    val errorMessage = loginResult.message
                    // Mostrar el mensaje de error al usuario
                }
            }
            _isLoading.value= false
        }
    }
}

