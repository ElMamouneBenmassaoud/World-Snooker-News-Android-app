package g58112.mobg5.snookernews.domaine.ranking

import android.util.Log
import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import g58112.mobg5.snookernews.repo.ranking.RankingRepository
import javax.inject.Inject

class GetRankUseCase @Inject constructor(
    private val rankingRepository: RankingRepository
){
    suspend operator fun invoke() : List<RankingItem> {
        return rankingRepository.getRankings()
    }
}