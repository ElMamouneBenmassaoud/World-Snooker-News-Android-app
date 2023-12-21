package g58112.mobg5.snookernews.ui.screens.ranking_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import g58112.mobg5.snookernews.domaine.item.RankingItem

@Composable
fun RankingScreen() {
    val rankingViewModel = hiltViewModel<RankingViewModel>()
    val rankings by rankingViewModel.rankings.collectAsState()


    LazyColumn {
        items(rankings) {ranking: RankingItem ->
            RankingCard(ranking = ranking)
        }
    }
}

@Composable
fun RankingCard(ranking: RankingItem) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
            .fillMaxSize()
    ){
        Column {
            Text(text = ranking.name)
            Text(text = ranking.abbreviation)
            Text(text = ranking.rank.toString())
            Text(text = ranking.prizeMoney.toString())
        }
    }
}