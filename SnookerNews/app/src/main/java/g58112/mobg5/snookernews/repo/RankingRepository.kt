package g58112.mobg5.snookernews.repo

import g58112.mobg5.snookernews.data.remote.RankingService
import g58112.mobg5.snookernews.data.remote.model.CompetitorRanking
import g58112.mobg5.snookernews.domaine.item.RankingItem
import g58112.mobg5.snookernews.domaine.item.toRankingItem
import javax.inject.Inject

class RankingRepository @Inject constructor(
    private val rankingService: RankingService
) {
    suspend fun getRankings(): List<RankingItem> {
        return rankingService.getRankings().map {
            it.toRankingItem()
        }
    }
}