package g58112.mobg5.snookernews.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import g58112.mobg5.snookernews.ui.theme.AppTheme
import g58112.mobg5.snookernews.R
import g58112.mobg5.snookernews.ui.theme.md_theme_light_scrim
import g58112.mobg5.snookernews.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val appUiState by loginViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = appUiState, block = {
        if (!appUiState.isMailWrong && appUiState.isEmailValidationAsked) {
            navigate()
            loginViewModel.resetAppUiState()
        }
    })

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val offsetLogo = 0.dp
        Image(
            painter = painterResource(id = R.drawable.snooker_icon_app),
            contentDescription = "The app logo of World Snooker News",
            modifier = Modifier
                .width(400.dp)
                .height(200.dp)
                .offset(y = -offsetLogo)
        )

        val offsetLayoutButton = 10.dp
        AppLayout(
            loginViewModel = loginViewModel,
            onUserMailChanged = { loginViewModel.updateUserEmail(it) },
            userMail = loginViewModel.userMail,
            onKeyboardDone = {
                loginViewModel.authenticate(loginViewModel.userMail, loginViewModel.userPassword)
            },
            isMailWrong = appUiState.isMailWrong,
            isEmailValidationAsked = appUiState.isEmailValidationAsked,
            onPasswordChanged = { loginViewModel.updateUserPassword(it) },
            userPassword = loginViewModel.userPassword,
            isPasswordWrong = appUiState.isPasswordWrong,
            modifier = Modifier
                .fillMaxSize()
                .offset(y = offsetLayoutButton)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = offsetLayoutButton),
                onClick = {
                    loginViewModel.authenticate(
                        loginViewModel.userMail,
                        loginViewModel.userPassword
                    )
                },
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 16.sp,
                    color = md_theme_light_scrim
                )
            }
        }
    }
}

@Composable
private fun AppLayout(
    loginViewModel: LoginViewModel,
    onUserMailChanged: (String) -> Unit,
    userMail: String,
    isMailWrong: Boolean,
    isEmailValidationAsked: Boolean,
    onPasswordChanged: (String) -> Unit,
    userPassword: String,
    isPasswordWrong: Boolean,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val authError = loginViewModel.authError

    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    var passwordVisible by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            Text(
                text = stringResource(id = R.string.instructions),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = userMail,
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onUserMailChanged,
                label = {
                    if (!loginViewModel.isValidEmail(userMail) && isEmailValidationAsked) {
                        Text(stringResource(R.string.wrong_mail))
                    } else {
                        Text(stringResource(R.string.enter_your_email))
                    }
                },

                isError = isMailWrong && isEmailValidationAsked,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                ),
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "Email")
                }
            )
            OutlinedTextField(
                value = userPassword,
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onPasswordChanged,
                label = { Text(stringResource(R.string.enter_your_password)) },
                isError = isPasswordWrong && isEmailValidationAsked,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description =
                        if (passwordVisible) stringResource(R.string.hide_password) else stringResource(
                            R.string.display_password
                        )
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(image, description)
                    }
                },
                maxLines = 1,
                leadingIcon = {
                    Icon(Icons.Filled.Password, contentDescription = "Password")
                }
            )

            if (authError) {
                ErrorDialog(onDismissRequest = loginViewModel::hideError)
            }
        }
    }
}

@Composable
private fun ErrorDialog(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest },
        title = {
            Text(
                stringResource(R.string.authentication_error),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Text(
                stringResource(R.string.wrong_login_or_password),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            Button(
                onClick = { onDismissRequest() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onError,
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(
                    stringResource(R.string.ok),
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        textContentColor = MaterialTheme.colorScheme.onSurface
    )
}

@Preview(showBackground = true)
@Composable
fun StartLoginScreenPreview(loginViewModel: LoginViewModel = viewModel()) {
    val navController = rememberNavController()
    AppTheme {
        LoginScreen(loginViewModel, { navController.navigate(BrusselsNavScreen.LogoESI.name) })
    }
}


