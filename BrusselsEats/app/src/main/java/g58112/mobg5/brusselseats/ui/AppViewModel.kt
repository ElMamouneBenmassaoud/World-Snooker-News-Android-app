package g58112.mobg5.brusselseats.ui

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

    private var usedMail: MutableSet<String> = mutableSetOf()

    var userMail by mutableStateOf("")

    init {
        resetEmail()
    }

    private fun resetEmail() {
        usedMail.clear()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun updateUserEmail(email: String){
        userMail = email
    }

    fun checkUserMail() {
        if (isValidEmail(userMail)) {
            _uiState.update { currentState -> currentState.copy(isMailWrong = false) }
        } else {
            _uiState.update { currentState -> currentState.copy(isMailWrong = true) }
            updateUserEmail("")
        }
    }

}
