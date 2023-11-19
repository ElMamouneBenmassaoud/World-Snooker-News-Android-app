package g58112.mobg5.snookernews.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.AuthApi
import com.example.marsphotos.network.LoginBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface SnookerUiState {
    object Success : SnookerUiState
    object Error : SnookerUiState
    object Loading : SnookerUiState
}


class AppViewModel : ViewModel() {
    var snookerUiState: SnookerUiState by mutableStateOf(SnookerUiState.Success)
        private set


    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()
    private val initialUiState = AppUiState()

    private var usedMail: MutableSet<String> = mutableSetOf()

    var userMail by mutableStateOf("")

    var userPassword by mutableStateOf("")

    init {
        resetEmail()
    }

    private fun resetEmail() {
        usedMail.clear()
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    }

    fun updateUserEmail(email: String) {
        userMail = email
    }

    fun updateUserPassword(password: String) {
        userPassword = password
    }
/*
    fun checkUserCredentials() {
        if (isValidPassword(userPassword)) {
            _uiState.update { currentState -> currentState.copy(isMailWrong = false, isPasswordWrong = false) }
            authenticate(userMail, userPassword)
        } else {
            _uiState.update { currentState -> currentState.copy(isMailWrong = true, isPasswordWrong = true) }
            updateUserEmail("")
            updateUserPassword("")
        }
        _uiState.update { currentState -> currentState.copy(isEmailValidationAsked = true) }
    }
*/


    fun resetAppUiState() {
        _uiState.value = initialUiState
    }

    fun authenticate(email: String, password: String) {
        viewModelScope.launch {
            try {
                snookerUiState = SnookerUiState.Loading
                val response = AuthApi.retrofitService.authenticate(
                    contentType = "application/json",
                    apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRuc3Jpdm54bGVlcWR0YnloZnR2Iiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5NzE0MDI2MSwiZXhwIjoyMDEyNzE2MjYxfQ.jgJ49-c9Z8iPQnLVTnPlfRZpKwyBKht-OY8wMTceSiM",
                    loginBody = LoginBody(email.trim(), password)
                )
                snookerUiState = SnookerUiState.Success

                if (response.isSuccessful) {
                    _uiState.update { currentState -> currentState.copy(isMailWrong = false, isPasswordWrong = false) }
                } else {
                    Log.d("test","authenticate: echec")
                    _uiState.update { currentState -> currentState.copy(isMailWrong = true, isPasswordWrong = true) }
                    updateUserPassword("")
                }
                _uiState.update { currentState -> currentState.copy(isEmailValidationAsked = true) }
            } catch (e: Exception) {
                snookerUiState = SnookerUiState.Error
            }
        }
    }
}