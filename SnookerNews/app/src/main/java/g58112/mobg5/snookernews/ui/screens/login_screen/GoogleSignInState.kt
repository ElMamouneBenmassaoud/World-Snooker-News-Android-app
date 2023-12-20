package g58112.mobg5.snookernews.ui.screens.login_screen

import com.google.firebase.auth.AuthResult

data class GoogleSignInState(
    val success: AuthResult? = null,
    val loading: Boolean = false,
    val error: String = ""
)
