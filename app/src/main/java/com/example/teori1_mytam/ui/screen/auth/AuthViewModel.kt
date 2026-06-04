package com.example.teori1_mytam.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teori1_mytam.data.repository.AuthRepository
import com.example.teori1_mytam.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.R.attr.name

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            when (val result = repository.login(email, password)) {
                is Resource.Success -> {
                    val data  = result.data
                    val token  = data.token ?: data.id ?: "logged_in"
                    val name   = data.name  ?: name
                    val mail   = data.email ?: email
                    val userId = data.id    ?: ""     // ← tambah
                    _uiState.value = AuthUiState.Success(token, name, mail, userId)
                }
                is Resource.Error -> _uiState.value = AuthUiState.Error(result.message)
                is Resource.Loading -> Unit
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            when (val result = repository.register(name, email, password)) {
                is Resource.Success -> {
                    val data  = result.data
                    val token  = data.token ?: data.id ?: "logged_in"
                    val name   = data.name  ?: name
                    val mail   = data.email ?: email
                    val userId = data.id    ?: ""     // ← tambah
                    _uiState.value = AuthUiState.Success(token, name, mail, userId)
                }
                is Resource.Error -> _uiState.value = AuthUiState.Error(result.message)
                is Resource.Loading -> Unit
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
}