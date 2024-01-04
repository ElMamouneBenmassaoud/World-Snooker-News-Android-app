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
import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import g58112.mobg5.snookernews.rankingplayer.RankingPlayer
import androidx.compose.ui.platform.LocalContext
import g58112.mobg5.snookernews.rankingplayercard.RankingPlayerCard

@Composable
fun RankingScreen() {
    val rankingViewModel = hiltViewModel<RankingViewModel>()
    val rankings by rankingViewModel.rankings.collectAsState()
    val context = LocalContext.current
    val toastMessage by rankingViewModel.toastMessage

    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            rankingViewModel.clearToastMessage()
        }
    }

    LazyColumn {
        items(rankings) { ranking: RankingItem ->
            RankingPlayerCard(
                playerName = ranking.name,
                abrv = ranking.abbreviation,
                prizeMoney = "Prize Money : " + ranking.prizeMoney.toString() + "$",
                rank = ranking.rank.toString(),
                onClick = {
                    rankingViewModel.addPlayerToFavorites(ranking)
                }
            )
        }
    }
}


@Preview
@Composable
fun RankingCardPreview() {
    RankingPlayer(
        playerName = "Mamoun benmassaoud",
        rank = "1",
        abrv = "Mams",
        prizeMoney = "100000$",
        onClick = {}
    )
}