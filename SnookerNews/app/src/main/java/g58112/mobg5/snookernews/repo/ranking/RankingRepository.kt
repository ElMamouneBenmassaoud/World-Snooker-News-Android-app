package g58112.mobg5.snookernews.repo.ranking

import android.util.Log
import g58112.mobg5.snookernews.data.remote.ApiService
import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import g58112.mobg5.snookernews.domaine.ranking.item.toRankingItem
import javax.inject.Inject

class RankingRepository @Inject constructor(
    private val rankingService: ApiService
) {
    suspend fun getRankings(): List<RankingItem> {
        return  rankingService.getRankings().flatMap { it.toRankingItem() }
    }
}