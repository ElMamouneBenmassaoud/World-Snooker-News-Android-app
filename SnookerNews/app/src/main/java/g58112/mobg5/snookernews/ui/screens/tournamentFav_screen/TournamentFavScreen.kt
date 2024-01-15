package g58112.mobg5.snookernews.ui.screens.tournamentFav_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import g58112.mobg5.snookernews.R
import g58112.mobg5.snookernews.domaine.tournament.item.CompetitionItem
import g58112.mobg5.snookernews.tournamentfavcard.TournamentFavCard

@Composable
fun TournamentFavScreen() {
    val tournamentFavViewModel = hiltViewModel<TournamentFavViewModel>()
    val toastMessage by tournamentFavViewModel.toastMessage
    val context = LocalContext.current
    val favoriteTournament by tournamentFavViewModel.favoriteTournaments.collectAsState()

    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            tournamentFavViewModel.clearToastMessage()
        }
    }

    if (favoriteTournament.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.no_favorite_tournament_at_the_moment),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyColumn {
            items(favoriteTournament) { competition: CompetitionItem ->
                TournamentFavCard(
                    name = competition.name ?: stringResource(R.string.emptyString),
                    categoryName = competition.nameCategory
                        ?: stringResource(R.string.international),
                    country = competition.countryCode ?: stringResource(R.string.eng),
                    gender = competition.gender ?: stringResource(R.string.mixed),
                    onClick = {
                        tournamentFavViewModel.removeTournamentFromFavorites(competition)
                    }
                )
            }
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