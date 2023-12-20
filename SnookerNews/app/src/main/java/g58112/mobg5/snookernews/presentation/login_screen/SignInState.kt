package g58112.mobg5.snookernews.presentation.login_screen

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)