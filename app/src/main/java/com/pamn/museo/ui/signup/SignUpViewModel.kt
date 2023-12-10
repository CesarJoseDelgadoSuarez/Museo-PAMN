package com.pamn.museo.ui.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.pamn.museo.data.AuthService
import com.pamn.museo.data.FirestoreService
import com.pamn.museo.data.UserService
import com.pamn.museo.model.AppScreens
import com.pamn.museo.model.FirestoreResult
import com.pamn.museo.model.LoginResult
import com.pamn.museo.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(private val userService: UserService) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> = _lastName

    private val _dateOfBirth = MutableLiveData<Timestamp>()

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

    fun onNameChanged(name: String) {
        _name.value = name
        updateSignUpButtonState()
    }

    fun onLastNameChanged(lastName: String) {
        _lastName.value = lastName
        updateSignUpButtonState()
    }

    fun onDateOfBirthSelected(date: Timestamp) {
        _dateOfBirth.value = date
    }

    private fun updateSignUpButtonState() {
        _signUpEnabled.value =
            isValidEmail(_email.value.toString()) && isValidPassword(_password.value.toString()) &&
                    _password.value == _confirmPassword.value && !_name.value.isNullOrBlank() && !_lastName.value.isNullOrBlank()
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean =
        password.length >= 6

    fun onSignUpSelected(navigateTo: (AppScreens) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = userService.SignUpWithEmailAndPassword(
                _email.value.toString(),
                _password.value.toString(),
                _name.value.toString(),
                _lastName.value.toString(),
                _dateOfBirth.value!!
            )
            when (result) {
                is LoginResult.Success -> {
                    navigateTo(AppScreens.UserInfo)
                }
                is LoginResult.Error -> {
                    _credentialsError.value = true
                    _isLoading.value = false
                }
            }
        }
    }
}
