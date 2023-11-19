package g58112.mobg5.snookernews.viewmodel.state

data class AppUiState(
    val isEmailValidationAsked: Boolean = false,
    val isMailWrong: Boolean = true,
    val isPasswordWrong: Boolean = true
)