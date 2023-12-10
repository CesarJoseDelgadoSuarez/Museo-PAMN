package com.pamn.museo.ui.userlogic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pamn.museo.data.AuthService
import com.pamn.museo.model.AppScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserLogicViewModel @Inject constructor(
private val authService: AuthService
): ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _screen = MutableLiveData<AppScreens>()
    val screen: LiveData<AppScreens> = _screen


    init {
        _isLoading.value = true
        if (authService.getCurrentUser() != null) {
            _screen.value = AppScreens.UserInfo
        }else{
            _screen.value = AppScreens.SignIn
        }
        _isLoading.value = false
    }

}

