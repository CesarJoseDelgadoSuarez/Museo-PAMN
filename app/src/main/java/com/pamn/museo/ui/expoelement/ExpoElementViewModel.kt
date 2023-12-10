package com.pamn.museo.ui.expoelement

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pamn.museo.data.ExpoelementService
import com.pamn.museo.model.ExpoElement
import com.pamn.museo.model.FirestoreResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpoElementViewModel @Inject constructor(
    private val expoElementService: ExpoelementService
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _expoElement = MutableLiveData<ExpoElement>()
    val expoElement: LiveData<ExpoElement> = _expoElement
    fun setExpoElement(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val firestoreResult = expoElementService.getElement(id)

            Log.w("ExpoElementViewModel", "setExpoElement: $firestoreResult");

            when (firestoreResult) {
                is FirestoreResult.Success -> {
                    _expoElement.value = firestoreResult.data
                }

                is FirestoreResult.Error -> {
                    Log.w("ExpoElementViewModel", firestoreResult.message);
                }
            }
            _isLoading.value = false
        }
    }
}
