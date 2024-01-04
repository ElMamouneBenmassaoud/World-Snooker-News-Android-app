package g58112.mobg5.snookernews.ui.screens.tournament_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import g58112.mobg5.snookernews.rankingplayer.RankingPlayer
import androidx.compose.ui.platform.LocalContext
import g58112.mobg5.snookernews.component1.Component1
import g58112.mobg5.snookernews.domaine.tournament.item.CompetitionItem
import g58112.mobg5.snookernews.rankingplayercard.RankingPlayerCard
import g58112.mobg5.snookernews.tournamentcard.TournamentCard

@Composable
fun TournamentScreen() {
    val TournamentViewModel = hiltViewModel<TournamentViewModel>()
    val tournaments by TournamentViewModel.tournament.collectAsState()
    val context = LocalContext.current
    val toastMessage by TournamentViewModel.toastMessage

    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            TournamentViewModel.clearToastMessage()
        }
    }

    LazyColumn {
        items(tournaments) { competition: CompetitionItem ->
            Log.d("TAG", "Test :$competition")

            TournamentCard(
                name = competition.name ?: "Inconnu",
                gender = competition.gender ?: "Mixed",
                categoryName = competition.nameCategory ?: "International",
                country = competition.country_code ?: "Eng",
                onClick = {
                    TournamentViewModel.addTournamentToFavorites(competition)
                }
            )
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