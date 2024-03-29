package g58112.mobg5.snookernews.ui.screens.signup_screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import g58112.mobg5.snookernews.R
import g58112.mobg5.snookernews.util.Constant
import g58112.mobg5.snookernews.ui.screens.login_screen.SignInViewModel
import g58112.mobg5.snookernews.ui.screens.BrusselsNavScreen
import kotlinx.coroutines.launch


@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
    viewModelSignIn: SignInViewModel = hiltViewModel(),
) {


    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModelSignIn.googleSignIn(credentials)
            } catch (it: ApiException) {
                print(it)
                it.printStackTrace()
            }
        }
    val googleSignInState = viewModelSignIn.googleState.value


    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)
    val stateSignIn = viewModelSignIn.signInState.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(R.string.create_account),
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
        )
        Text(
            text = stringResource(R.string.enter_your_credential_s_to_register),
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp, color = Color.Gray,

            )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            label = { Text(stringResource(R.string.enter_your_email)) },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                disabledLabelColor = Color.Blue, unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                email = it

            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                disabledLabelColor = Color.Cyan, unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                password = it
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            label = { Text(stringResource(R.string.enter_your_password)) },
            visualTransformation =
            if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
            leadingIcon = {
                Icon(Icons.Filled.Password, contentDescription = "Password")
            }
        )
        Button(
            onClick = {
                scope.launch {
                    viewModel.registerUser(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF69D3FF),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                color = Color.White,
                modifier = Modifier
                    .padding(7.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (state.value?.isLoading == true || stateSignIn.value?.isLoading == true) {
                CircularProgressIndicator()
            }
        }
        Button(
            onClick = {
                navController.navigate(BrusselsNavScreen.SignIn.name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 30.dp, end = 30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF234D24), contentColor = Color.White
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = stringResource(R.string.already_have_an_account),
                color = Color.White,
                modifier = Modifier.padding(7.dp)
            )
        }
        Text(
            modifier = Modifier
                .padding(
                    top = 20.dp,
                ),
            text = stringResource(R.string.or_connect_with),
            fontWeight = FontWeight.Medium, color = Color.Gray
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(Constant.ServerClient)
                    .build()

                val googleSingInClient = GoogleSignIn.getClient(context, gso).apply { signOut() }

                launcher.launch(googleSingInClient.signInIntent)

            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(50.dp),
                    tint = Color.Unspecified
                )
            }

        }
    }
    LaunchedEffect(key1 = stateSignIn.value?.isSuccess) {
        scope.launch {
            if (stateSignIn.value?.isSuccess?.isNotEmpty() == true) {
                val success = stateSignIn.value?.isSuccess
                Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                navController.navigate(BrusselsNavScreen.LogoESI.name)
            }
        }
    }

    LaunchedEffect(key1 = stateSignIn.value?.isError) {
        scope.launch {
            if (stateSignIn.value?.isError?.isNotEmpty() == true) {
                val error = stateSignIn.value?.isError
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = googleSignInState.success) {
        scope.launch {
            if (googleSignInState.success != null) {
                Toast.makeText(context,
                    "Sign In Success",
                    Toast.LENGTH_LONG
                ).show()
                navController.navigate(BrusselsNavScreen.LogoESI.name)
            }
        }
    }

    LaunchedEffect(key1 = googleSignInState.error) {
        scope.launch {
            if (googleSignInState.error.isNotEmpty()) {
                Toast.makeText(
                    context,
                    "Sign In Error: ${googleSignInState.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    LaunchedEffect(key1 = state.value?.isSuccess) {
        scope.launch {
            if (state.value?.isSuccess?.isNotEmpty() == true) {
                val success = state.value?.isSuccess
                Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                navController.navigate(BrusselsNavScreen.SignIn.name)
            }
        }
    }
    LaunchedEffect(key1 = state.value?.isError) {
        scope.launch {
            if (state.value?.isError?.isNotBlank() == true) {
                val error = state.value?.isError
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }
        }
    }
}