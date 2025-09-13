package com.sharif.simplenote.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharif.simplenote.data.models.UserInfo
import com.sharif.simplenote.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserState(
    val user: UserInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()


    fun getUserInfo() {
        viewModelScope.launch {
            _userState.value = UserState(isLoading = true)
            try {
                val user = authRepository.getUserInfo()
                _userState.value = UserState(user = user)
            } catch (e: Exception) {
                _userState.value = UserState(error = e.message)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}