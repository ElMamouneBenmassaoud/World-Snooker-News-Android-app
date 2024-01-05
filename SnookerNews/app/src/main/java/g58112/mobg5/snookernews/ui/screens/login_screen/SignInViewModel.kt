package g58112.mobg5.snookernews.ui.screens.login_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import g58112.mobg5.snookernews.data.AuthRepository
import g58112.mobg5.snookernews.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Sign In screen.
 *
 * This ViewModel handles the sign-in logic for the application, including Google sign-in and regular email/password authentication.
 * It interacts with [AuthRepository] to perform authentication operations and updates the UI state accordingly.
 *
 * @property repository The authentication repository used for performing sign-in operations.
 */
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    private val _googleState = mutableStateOf(GoogleSignInState())
    val googleState: State<GoogleSignInState> = _googleState

    /**
     * Initiates the Google sign-in process.
     *
     * This function triggers the Google sign-in process using [AuthCredential] and updates the Google sign-in state accordingly.
     *
     * @param credential The authentication credentials obtained from Google Sign-In.
     */
    fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        repository.googleSignIn(credential).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _googleState.value = GoogleSignInState(success = result.data)
                }
                is Resource.Loading -> {
                    _googleState.value = GoogleSignInState(loading = true)
                }
                is Resource.Error -> {
                    _googleState.value = GoogleSignInState(error = result.message!!)
                }
            }


        }
    }

    /**
     * Logs in a user using their email and password.
     *
     * This function performs the sign-in operation using email and password, and updates the sign-in state based on the result.
     *
     * @param email The user's email address.
     * @param password The user's password.
     */
    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _signInState.send(SignInState(isSuccess = "Sign In Success "))
                }
                is Resource.Loading -> {
                    _signInState.send(SignInState(isLoading = true))
                }
                is Resource.Error -> {

                    _signInState.send(SignInState(isError = result.message))
                }
            }

        }
    }

    /**
     * Signs out the currently logged-in user.
     *
     * This function performs the sign-out operation and is to be called from within a coroutine.
     */
    suspend fun signOut() = viewModelScope.launch {
        repository.signOut()
    }

}