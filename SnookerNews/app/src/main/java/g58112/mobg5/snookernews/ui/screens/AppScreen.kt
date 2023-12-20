/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package g58112.mobg5.snookernews.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.navigation.compose.NavHost
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import g58112.mobg5.snookernews.R
import g58112.mobg5.snookernews.ui.screens.login_screen.SignInScreen
import g58112.mobg5.snookernews.ui.screens.login_screen.SignInViewModel
import g58112.mobg5.snookernews.ui.screens.profile_screen.UserProfileScreen
import g58112.mobg5.snookernews.ui.screens.signup_screen.SignUpScreen
import g58112.mobg5.snookernews.ui.theme.AppTheme
import g58112.mobg5.snookernews.viewmodel.LoginViewModel
import g58112.mobg5.snookernews.viewmodel.SnookerUiState

enum class BrusselsNavScreen {
    Login,
    LogoESI,
    About,
    SignIn,
    SignUp,
}

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel()
) {
    val snookerUiState = loginViewModel.snookerUiState

    val navController = rememberNavController()

    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route

    val currentRoute = currentScreen?.let { BrusselsNavScreen.valueOf(it) }

    Scaffold(
        topBar = {
            if (currentRoute != BrusselsNavScreen.SignIn && currentRoute != BrusselsNavScreen.SignUp) {
                val title = currentRoute?.let { getScreenTitle(it) } ?: "Home"
                CustomTopBar(
                    onNavigationIconClick = { navController.popBackStack() },
                    text = title,
                    signInViewModel = viewModel(), // Ici, nous passons le SignInViewModel
                    navController = navController
                )
            } else {
                val offsetLogo = 0.dp
                Image(
                    painter = painterResource(id = R.drawable.snooker_icon_app),
                    contentDescription = "The app logo of World Snooker News",
                    modifier = Modifier
                        .width(400.dp)
                        .height(200.dp)
                        .offset(y = -offsetLogo)
                )
            }
        },
        bottomBar = {
            if (currentRoute != BrusselsNavScreen.SignIn && currentRoute != BrusselsNavScreen.SignUp) {
                BottomNavigationBar(navController = navController)
            }
        }) { innerPadding ->
        when (snookerUiState) {
            is SnookerUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is SnookerUiState.Success -> NavHost(
                navController = navController,
                startDestination = BrusselsNavScreen.SignIn.name,
                modifier = modifier.padding(innerPadding)
            ) {
                composable(route = BrusselsNavScreen.SignIn.name) {
                    SignInScreen(navController = navController)

                }
                composable(route = BrusselsNavScreen.SignUp.name) {
                    SignUpScreen(navController = navController)

                }

                composable(route = BrusselsNavScreen.Login.name) {
                    LoginScreen(
                        loginViewModel = loginViewModel,
                        navigate = { navController.navigate(BrusselsNavScreen.LogoESI.name) }
                    )
                }

                composable(route = BrusselsNavScreen.LogoESI.name) {
                    EsiCenteredImage()
                }
                composable(route = BrusselsNavScreen.About.name) {
                    UserProfileScreen()
                }
            }

            is SnookerUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
        }

        AuthorCredits(modifier = modifier)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home Icon") },
            label = { Text("Home") },
            selected = navController.currentDestination?.route == BrusselsNavScreen.LogoESI.name,
            onClick = { navController.navigate(BrusselsNavScreen.LogoESI.name) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Info, contentDescription = "Info icon") },
            label = { Text("About") },
            selected = navController.currentDestination?.route == BrusselsNavScreen.About.name,
            onClick = { navController.navigate(BrusselsNavScreen.About.name) }
        )
    }
}

@Composable
fun CustomTopBar(
    onNavigationIconClick: () -> Unit,
    text: String,
    signInViewModel: SignInViewModel, // Ajoutez le ViewModel comme paramètre
    navController: NavController
) {
    var signOutRequested by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Green
                    )
                }
                Spacer(Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.outside_icon_app),
                        contentDescription = "Logo",
                        modifier = Modifier.size(70.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = text, style = MaterialTheme.typography.titleLarge)

                }

                Spacer(Modifier.weight(1f))
            }
        },
        actions = {
            IconButton(onClick = { signOutRequested = true }) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Logout",
                    tint = Color.Red
                )
            }
        },
        modifier = Modifier.height(56.dp)
    )


    if (signOutRequested) {
        LaunchedEffect(signOutRequested) {
            signInViewModel.signOut()
            navController.navigate(BrusselsNavScreen.SignIn.name) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
            signOutRequested = false // Réinitialiser l'état après la déconnexion
        }
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}


/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun AuthorCredits(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Copyright © 2023 El Mamoune Benmassaoud",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

fun getScreenTitle(screen: BrusselsNavScreen): String {
    return when (screen) {
        BrusselsNavScreen.Login -> "Login"
        BrusselsNavScreen.LogoESI -> "Home"
        BrusselsNavScreen.About -> "About"
        BrusselsNavScreen.SignIn -> "Sign In"
        BrusselsNavScreen.SignUp -> "Sign Up"
        // Ajoutez plus de cas si nécessaire
    }
}


@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    AppTheme {
        AppScreen()
    }
}
