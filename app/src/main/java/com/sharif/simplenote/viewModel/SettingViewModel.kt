package com.sharif.simplenote.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharif.simplenote.data.models.UserInfo
import com.sharif.simplenote.data.models.ChangePasswordResponse
import com.sharif.simplenote.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserState(
    val isLoading: Boolean = false,
    val user: UserInfo? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)

data class ChangePasswordState(
    val isLoading: Boolean = false,
    val response: ChangePasswordResponse? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    private val _changePasswordState = MutableStateFlow(ChangePasswordState())
    val changePasswordState: StateFlow<ChangePasswordState> = _changePasswordState.asStateFlow()

    fun getUserInfo() {
        viewModelScope.launch {
            _userState.value = UserState(isLoading = true)
            try {
                val user = authRepository.getUserInfo()
                _userState.value = UserState(user = user, isSuccess = user != null)
            } catch (e: Exception) {
                _userState.value = UserState(error = e.message)
            }
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            _changePasswordState.value = ChangePasswordState(isLoading = true)
            try {
                val response = authRepository.changePassword(oldPassword, newPassword)
                _changePasswordState.value = ChangePasswordState(
                    response = response,
                    isSuccess = response != null
                )
            } catch (e: Exception) {
                _changePasswordState.value = ChangePasswordState(error = e.message)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}