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


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val _signUpState  = Channel<SignInState>()
    val signUpState  = _signUpState.receiveAsFlow()


    fun registerUser(email:String, password:String) = viewModelScope.launch {
        repository.registerUser(email, password).collect{result ->
            when(result){
                is Resource.Success ->{
                    _signUpState.send(SignInState(isSuccess = "Sign Up Success "))
                }
                is Resource.Loading ->{
                    _signUpState.send(SignInState(isLoading = true))
                }
                is Resource.Error ->{

                    _signUpState.send(SignInState(isError = result.message))
                }
            }

        }
    }

}