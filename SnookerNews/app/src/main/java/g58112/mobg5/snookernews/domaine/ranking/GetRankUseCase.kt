package g58112.mobg5.snookernews.domaine.ranking

import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import g58112.mobg5.snookernews.repo.ranking.RankingRepository
import javax.inject.Inject

/**
 * Use case for fetching rankings in the snooker news domain.
 *
 * This class encapsulates the logic for retrieving player rankings.
 * It serves as an intermediary between the repository layer and the UI/view model layer,
 * abstracting the details of data fetching.
 *
 * @property rankingRepository The repository responsible for fetching ranking data.
 */
class GetRankUseCase @Inject constructor(
    private val rankingRepository: RankingRepository
) {

    /**
     * Retrieves the list of player rankings.
     *
     * This suspend function is to be called within a coroutine or another suspend function.
     * It delegates the call to the [rankingRepository] to fetch the player rankings and returns the result.
     *
     * @return A list of [RankingItem] representing player rankings.
     */
    suspend operator fun invoke(): List<RankingItem> {
        return rankingRepository.getRankings()
    }
}
