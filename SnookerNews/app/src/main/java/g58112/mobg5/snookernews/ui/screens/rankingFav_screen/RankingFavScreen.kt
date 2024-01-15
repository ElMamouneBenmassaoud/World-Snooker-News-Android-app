package g58112.mobg5.snookernews.ui.screens.rankingFav_screen

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
import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import g58112.mobg5.snookernews.rankingplayer.RankingPlayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import g58112.mobg5.snookernews.R

@Composable
fun RankingFavScreen() {
    val rankingFavViewModel = hiltViewModel<RankingFavViewModel>()
    val toastMessage by rankingFavViewModel.toastMessage
    val context = LocalContext.current
    val favoriteRankings by rankingFavViewModel.favoriteRankings.collectAsState()

    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            rankingFavViewModel.clearToastMessage()
        }
    }

    if (favoriteRankings.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.no_favorite_players_at_the_moment), style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn {
            items(favoriteRankings) { ranking: RankingItem ->
                RankingPlayer(
                    playerName = ranking.name,
                    abrv = ranking.abbreviation,
                    prizeMoney = stringResource(R.string.money, ranking.prizeMoney),
                    rank = ranking.rank.toString(),
                    onClick = {
                        Toast.makeText(context, "Player removed from favorites.", Toast.LENGTH_SHORT)
                            .show()
                        rankingFavViewModel.removePlayerFromFavorites(ranking)
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun RankingFavCardPreview() {
    RankingPlayer(
        playerName = "Mamoun benmassaoud",
        rank = "1",
        abrv = "Mams",
        prizeMoney = "100000$",
        onClick = {}
    )
}