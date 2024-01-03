package g58112.mobg5.snookernews.ui.screens.ranking_screen

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import g58112.mobg5.snookernews.domaine.item.RankingItem
import g58112.mobg5.snookernews.rankingplayer.RankingPlayer
import androidx.compose.ui.platform.LocalContext
@Composable
fun RankingFavScreen() {
    val rankingFavViewModel = hiltViewModel<RankingFavViewModel>()
    val toastMessage by rankingFavViewModel.toastMessage
    val context = LocalContext.current
    val favoriteRankings by rankingFavViewModel.favoriteRankings.collectAsState()

    LaunchedEffect(Unit) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            rankingFavViewModel.clearToastMessage()
        }
    }

    LazyColumn {
        items(favoriteRankings) { ranking: RankingItem ->
            RankingPlayer(
                playerName = ranking.name,
                abrv = ranking.abbreviation,
                prizeMoney = "Prize Money : " + ranking.prizeMoney.toString() + "$",
                rank = ranking.rank.toString(),
                onClick = {
                    Toast.makeText(context, "Joueur supprimé des favoris.", Toast.LENGTH_SHORT).show()
                    rankingFavViewModel.removePlayerFromFavorites(ranking)
                }
            )
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