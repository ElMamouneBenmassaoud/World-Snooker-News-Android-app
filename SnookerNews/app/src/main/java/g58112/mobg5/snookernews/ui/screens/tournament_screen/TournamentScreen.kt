package g58112.mobg5.snookernews.ui.screens.tournament_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import g58112.mobg5.snookernews.rankingplayer.RankingPlayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import g58112.mobg5.snookernews.domaine.tournament.item.CompetitionItem
import g58112.mobg5.snookernews.tournamentcard.TournamentCard

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TournamentScreen() {
    val keyboardController = LocalSoftwareKeyboardController.current

    val tournamentViewModel = hiltViewModel<TournamentViewModel>()
    val tournaments by tournamentViewModel.tournament.collectAsState()
    val context = LocalContext.current
    val toastMessage by tournamentViewModel.toastMessage

    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            tournamentViewModel.clearToastMessage()
        }
    }
    Column {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                tournamentViewModel.getTournamentsByName(it)
            },
            label = { Text("Search a player") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                tournamentViewModel.getTournamentsByName(searchQuery)
                keyboardController?.hide()
            }),
            trailingIcon = {
                IconButton(onClick = { tournamentViewModel.getTournamentsByName(searchQuery) }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        )
        LazyColumn {
            items(tournaments) { competition: CompetitionItem ->
                TournamentCard(
                    name = competition.name ?: "Inconnu",
                    gender = competition.gender ?: "Mixed",
                    categoryName = competition.nameCategory ?: "International",
                    country = competition.country_code ?: "Eng",
                    onClick = {
                        tournamentViewModel.addTournamentToFavorites(competition)
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun TournamentardPreview() {
    RankingPlayer(
        playerName = "Mamoun benmassaoud",
        rank = "1",
        abrv = "Mams",
        prizeMoney = "100000$",
        onClick = {}
    )
}