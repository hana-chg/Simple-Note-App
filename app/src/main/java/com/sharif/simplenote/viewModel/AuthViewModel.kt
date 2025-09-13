package com.sharif.simplenote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharif.simplenote.data.models.TokenObtainPair
import com.sharif.simplenote.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val isLoading: Boolean = false,
    val token: TokenObtainPair? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            try {
                val token = authRepository.login(username, password)
                _authState.value = AuthState(token = token, isSuccess = token != null)
            } catch (e: Exception) {
                _authState.value = AuthState(error = e.message)
            }
        }
    }

    fun register(username: String, email: String, password: String, firstName: String?, lastName: String?) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            try {
                val response = authRepository.register(username, email, password, firstName, lastName)
                _authState.value = AuthState(isSuccess = response != null)
            } catch (e: Exception) {
                _authState.value = AuthState(error = e.message)
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return runBlocking { authRepository.isLoggedIn() }
    }
}