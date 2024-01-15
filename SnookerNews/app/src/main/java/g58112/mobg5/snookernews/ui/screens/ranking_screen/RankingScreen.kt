package g58112.mobg5.snookernews.ui.screens.ranking_screen

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import g58112.mobg5.snookernews.rankingplayer.RankingPlayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import g58112.mobg5.snookernews.R
import g58112.mobg5.snookernews.rankingplayercard.RankingPlayerCard

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RankingScreen() {
    val keyboardController = LocalSoftwareKeyboardController.current

    val rankingViewModel = hiltViewModel<RankingViewModel>()
    val rankings by rankingViewModel.rankings.collectAsState()
    val context = LocalContext.current
    val toastMessage by rankingViewModel.toastMessage

    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            rankingViewModel.clearToastMessage()
        }
    }

    Column {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                rankingViewModel.getRankingsByName(it)
            },
            label = { Text(stringResource(R.string.search_a_player)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                rankingViewModel.getRankingsByName(searchQuery)
                keyboardController?.hide()
            }),
            trailingIcon = {
                IconButton(onClick = { rankingViewModel.getRankingsByName(searchQuery) }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        )

        LazyColumn {
            items(rankings) { ranking: RankingItem ->
                RankingPlayerCard(
                    playerName = ranking.name,
                    abrv = ranking.abbreviation,
                    prizeMoney = stringResource(R.string.money, ranking.prizeMoney),
                    rank = ranking.rank.toString(),
                    onClick = {
                        rankingViewModel.addPlayerToFavorites(ranking)
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun RankingCardPreview() {
    Column {

        TextField(
            value = "",
            onValueChange = { },
            label = { Text(stringResource(R.string.search_a_player)) },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { }) {
            Text("Search")
        }
        RankingPlayer(
            playerName = "Mamoun benmassaoud",
            rank = "1",
            abrv = "Mams",
            prizeMoney = "100000$",
            onClick = {}
        )
    }
}