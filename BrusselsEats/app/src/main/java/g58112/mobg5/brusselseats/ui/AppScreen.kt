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
package g58112.mobg5.brusselseats.ui

import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme

enum class BrusselsNavScreen() {
    Login,
    Logo_ESI,
}
@Composable
fun AppScreen(
    appViewModel: AppViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    Scaffold { innerPadding ->
        val gameUiState by appViewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = BrusselsNavScreen.Login.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = BrusselsNavScreen.Login.name) {
                LoginScreen(appViewModel = appViewModel, navController = navController)
            }

            composable(route = BrusselsNavScreen.Logo_ESI.name) {
                LargeCenteredImage()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    AppTheme {
        AppScreen()
    }
}
