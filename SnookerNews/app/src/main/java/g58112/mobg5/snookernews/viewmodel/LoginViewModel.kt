package g58112.mobg5.snookernews.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.internal.ApiKey
import g58112.mobg5.snookernews.network.AuthApi
import g58112.mobg5.snookernews.network.AuthResponse
import g58112.mobg5.snookernews.network.LoginBody
import g58112.mobg5.snookernews.util.Constant.API_KEY
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

                val response = AuthApi.retrofitService.authenticate(
                    apiKey = API_KEY
                )


                if (response.isSuccessful) {
                    _authData.value = response.body()

                    Log.d("Auth", "authenticate: Success ${response.body()}")


                } else {
                    Log.d("Auth", "authenticate: Failure")
                }
            } catch (e: Exception) {
                Log.d("Auth", "authenticateException: ${e.message}")
            }
        }
    }
}