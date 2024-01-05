package g58112.mobg5.snookernews.ui.screens.tournamentFav_screen

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import g58112.mobg5.snookernews.rankingplayer.RankingPlayer
import androidx.compose.ui.platform.LocalContext
import g58112.mobg5.snookernews.domaine.tournament.item.CompetitionItem
import g58112.mobg5.snookernews.tournamentfavcard.TournamentFavCard

@Composable
fun TournamentFavScreen() {
    val tournamentFavViewModel = hiltViewModel<TournamentFavViewModel>()
    val toastMessage by tournamentFavViewModel.toastMessage
    val context = LocalContext.current
    val favoriteTournament by tournamentFavViewModel.favoriteTournaments.collectAsState()

    LaunchedEffect(Unit) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            tournamentFavViewModel.clearToastMessage()
        }
    }

    LazyColumn {
        items(favoriteTournament) { competition: CompetitionItem ->
            TournamentFavCard(
                name = competition.name ?: "",
                categoryName = competition.nameCategory ?: "International",
                country = competition.country_code ?: "Eng",
                gender = competition.gender ?: "Mixed",
                onClick = {
                    tournamentFavViewModel.removeTournamentFromFavorites(competition)
                }
            )
        }
    }
}


@Preview
@Composable
fun RankingFavCardPreview() {
    TournamentFavCard(
        name = "",
        categoryName = "International",
        country = "Eng",
        gender = "Mixed"
    )
}