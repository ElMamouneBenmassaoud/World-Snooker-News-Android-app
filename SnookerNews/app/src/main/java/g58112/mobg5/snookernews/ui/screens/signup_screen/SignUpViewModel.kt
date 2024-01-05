package g58112.mobg5.snookernews.ui.screens.signup_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import g58112.mobg5.snookernews.data.AuthRepository
import g58112.mobg5.snookernews.ui.screens.login_screen.SignInState
import g58112.mobg5.snookernews.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Sign Up screen.
 *
 * This ViewModel handles the operations related to registering a new user,
 * including communicating with the authentication repository and updating the UI state based on the registration process.
 *
 * @property repository The authentication repository used for performing user registration operations.
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val _signUpState = Channel<SignInState>()
    val signUpState = _signUpState.receiveAsFlow()


    /**
     * Registers a new user with the provided email and password.
     *
     * This function triggers the user registration process using the provided credentials,
     * updates the sign-up state based on the result of the registration operation, and
     * handles different states such as success, loading, and error.
     *
     * @param email The email address of the new user.
     * @param password The password for the new user.
     */
    fun registerUser(email: String, password: String) = viewModelScope.launch {
        repository.registerUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _signUpState.send(SignInState(isSuccess = "Sign Up Success "))
                }

                is Resource.Loading -> {
                    _signUpState.send(SignInState(isLoading = true))
                }

                is Resource.Error -> {

                    _signUpState.send(SignInState(isError = result.message))
                }
            }

        }
    }

}