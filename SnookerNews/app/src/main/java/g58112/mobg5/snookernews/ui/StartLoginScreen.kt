package g58112.mobg5.snookernews.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import g58112.mobg5.snookernews.ui.theme.AppTheme
import g58112.mobg5.snookernews.R
import g58112.mobg5.snookernews.ui.theme.md_theme_light_scrim

@Composable
fun LoginScreen(
    appViewModel: AppViewModel,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val appUiState by appViewModel.uiState.collectAsState()
/*
    LaunchedEffect(key1 = appUiState, block = {
        if (!appViewModel.uiState.value.isMailWrong && appViewModel.userMail.isNotEmpty()) {
            navigate()
        }
    })
*/
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val offsetLogo = 30.dp
        Image(
            painter = painterResource(id = R.drawable.snooker_logo),
            contentDescription = "The app logo 'BruxEats'",
            modifier = Modifier
                .width(600.dp)
                .height(300.dp)
                .offset(y = -offsetLogo)
        )

        val offsetLayoutButton = 80.dp

        AppLayout(
            onUserMailChanged = { appViewModel.updateUserEmail(it) },
            userMail = appViewModel.userMail,
            onKeyboardDone = {
                appViewModel.checkUserMail()
                if (!appViewModel.uiState.value.isMailWrong){
                    navigate()
                }
            },
            isMailWrong = appUiState.isMailWrong,
            modifier = Modifier
                .fillMaxSize()
                .offset(y = -offsetLayoutButton)
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
                    .offset(y = -offsetLayoutButton),
                onClick = {
                    appViewModel.checkUserMail()
                    if (!appViewModel.uiState.value.isMailWrong){
                        navigate()
                    }
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
fun AppLayout(
    onUserMailChanged: (String) -> Unit,
    userMail: String,
    isMailWrong: Boolean,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

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
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            OutlinedTextField(
                value = userMail,
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onUserMailChanged,
                label = {
                    if (isMailWrong) {
                        Text(stringResource(R.string.wrong_mail))
                    } else {
                        Text(stringResource(R.string.enter_your_email))
                    }
                },

                isError = isMailWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
        }
    }
}


@Composable
fun LargeCenteredImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.esi_logo),
            contentDescription = "Logo esi he2b",
            modifier = Modifier.size(300.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StartLoginScreenPreview(appViewModel: AppViewModel = viewModel()) {
    val navController = rememberNavController()
    AppTheme {
        LoginScreen(appViewModel, {navController.navigate(BrusselsNavScreen.LogoESI.name)})
    }
}


