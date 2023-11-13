package g58112.mobg5.snookernews.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()
    private val initialUiState = AppUiState()

    private var usedMail: MutableSet<String> = mutableSetOf()

    var userMail by mutableStateOf("")

    init {
        resetEmail()
    }

    private fun resetEmail() {
        usedMail.clear()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    }

    fun updateUserEmail(email: String) {
        userMail = email
    }

    fun checkUserMail() {
        if (isValidEmail(userMail)) {
            _uiState.update { currentState -> currentState.copy(isMailWrong = false) }
        } else {
            _uiState.update { currentState -> currentState.copy(isMailWrong = true) }
            updateUserEmail("")
        }
        _uiState.update { currentState -> currentState.copy(isEmailValidationAsked = true) }
    }

    fun resetAppUiState() {
        _uiState.value = initialUiState
    }
}
