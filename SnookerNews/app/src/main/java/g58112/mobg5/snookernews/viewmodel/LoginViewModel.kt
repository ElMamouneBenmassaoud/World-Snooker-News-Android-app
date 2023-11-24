package g58112.mobg5.snookernews.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import g58112.mobg5.snookernews.network.AuthApi
import g58112.mobg5.snookernews.network.AuthResponse
import g58112.mobg5.snookernews.network.LoginBody
import g58112.mobg5.snookernews.viewmodel.state.AppUiState
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

/**
 * ViewModel for managing UI state and interactions in the Snooker app.
 */
class LoginViewModel : ViewModel() {

    /**
     * En déclarant private set après la déclaration d'une propriété, cela signifie que cette propriété
     * peut être lue de l'extérieur de la classe, mais elle ne peut être modifiée qu'à l'intérieur de cette même classe.
     */
    var snookerUiState: SnookerUiState by mutableStateOf(SnookerUiState.Success)
        private set

    var authError by mutableStateOf(false)
        private set

    /**
     * Resets the authentication error flag to false.
     */
    fun hideError() {
        authError = false
    }

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()
    private val initialUiState = AppUiState()

    private var usedMail: MutableSet<String> = mutableSetOf()

    var userMail by mutableStateOf("")

    var userPassword by mutableStateOf("")

    private val _authData = MutableLiveData<AuthResponse>()
    val authData: LiveData<AuthResponse> = _authData

    init {
        resetEmail()
    }

    /**
     * Clears the list of used emails.
     */
    private fun resetEmail() {
        usedMail.clear()
    }

    /**
     * Checks if the provided email string is a valid email format.
     *
     * @param email The email string to validate.
     * @return True if the email is valid, false otherwise.
     */
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    }

    /**
     * Updates the user email state.
     *
     * @param email The new email to be set.
     */
    fun updateUserEmail(email: String) {
        userMail = email
    }

    /**
     * Updates the user password state.
     *
     * @param password The new password to be set.
     */
    fun updateUserPassword(password: String) {
        userPassword = password
    }

    /**
     * Resets the UI state to its initial value.
     */
    fun resetAppUiState() {
        _uiState.value = initialUiState
    }

    /**
     * Authenticates a user with the provided email and password.
     * Updates the UI state based on the result of the authentication.
     *
     * @param email The user's email address.
     * @param password The user's password.
     */
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
                    _authData.value = response.body()

                    Log.d("Auth", "authenticate: Success")
                    _uiState.update { currentState ->
                        currentState.copy(
                            isMailWrong = false,
                            isPasswordWrong = false
                        )
                    }
                    authError = false
                } else {
                    Log.d("Auth", "authenticate: Failure")
                    _uiState.update { currentState ->
                        currentState.copy(
                            isMailWrong = true,
                            isPasswordWrong = true
                        )
                    }
                    updateUserPassword("")
                    authError = true
                }
                _uiState.update { currentState -> currentState.copy(isEmailValidationAsked = true) }
            } catch (e: Exception) {
                Log.d("Auth", "authenticateException: ${e.message}")
                snookerUiState = SnookerUiState.Error
            }
        }
    }
}