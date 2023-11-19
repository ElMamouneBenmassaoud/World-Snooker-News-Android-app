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
package g58112.mobg5.snookernews.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.navigation.compose.NavHost
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import g58112.mobg5.snookernews.ui.theme.AppTheme

enum class BrusselsNavScreen {
    Login,
    LogoESI,
    About,
}

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
) {
    val snookerUiState = appViewModel.snookerUiState

    val navController = rememberNavController()

    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentScreen != BrusselsNavScreen.Login.name) {
                BottomNavigationBar(navController = navController)
            }
        }) { innerPadding ->
        when (snookerUiState) {
            is SnookerUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is SnookerUiState.Success -> NavHost(
                navController = navController,
                startDestination = BrusselsNavScreen.Login.name,
                modifier = modifier.padding(innerPadding)
            ) {
                composable(route = BrusselsNavScreen.Login.name) {
                    LoginScreen(
                        appViewModel = appViewModel,
                        navigate = { navController.navigate(BrusselsNavScreen.LogoESI.name) }
                    )
                }

                composable(route = BrusselsNavScreen.LogoESI.name) {
                    LargeCenteredImage()
                }
                composable(route = BrusselsNavScreen.About.name) {
                    ProfileScreen(userName = "Mamoun", school = "ESI", course = "Mobg5", group = "E11", userEmail = appViewModel.userMail)
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
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = navController.currentDestination?.route == BrusselsNavScreen.LogoESI.name,
            onClick = { navController.navigate(BrusselsNavScreen.LogoESI.name) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Info, contentDescription = null) },
            label = { Text("About") },
            selected = navController.currentDestination?.route == BrusselsNavScreen.About.name,
            onClick = { navController.navigate(BrusselsNavScreen.About.name) }
        )

        // Vous pouvez ajouter d'autres items si nécessaire
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

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    AppTheme {
        AppScreen()
    }
}
