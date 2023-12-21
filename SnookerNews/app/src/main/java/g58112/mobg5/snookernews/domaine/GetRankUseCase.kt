package g58112.mobg5.snookernews.domaine

import g58112.mobg5.snookernews.domaine.item.RankingItem
import g58112.mobg5.snookernews.repo.RankingRepository
import javax.inject.Inject

class GetRankUseCase @Inject constructor(
    private val rankingRepository: RankingRepository
){
    suspend operator fun invoke() : List<RankingItem> {
        return rankingRepository.getRankings()
    }
}