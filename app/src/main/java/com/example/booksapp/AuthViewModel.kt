package com.example.booksapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel:ViewModel() {
    private val _skipAnimation = MutableStateFlow(false)
    val skipAnimation = _skipAnimation.asStateFlow()

    fun disableSignUpEntryAnimation(){
        viewModelScope.launch {
            if(_skipAnimation.value == false) {
                delay(1500)
                _skipAnimation.value = true
            }
        }
    }

}