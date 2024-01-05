package g58112.mobg5.snookernews.repo.ranking

import g58112.mobg5.snookernews.data.remote.ApiService
import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import g58112.mobg5.snookernews.domaine.ranking.item.toRankingItem
import javax.inject.Inject

/**
 * Repository class for fetching ranking data.
 *
 * This class acts as a data layer for retrieving player rankings, abstracting the details of data source and transformation.
 *
 * @property rankingService The service used for making API calls to fetch ranking data.
 */
class RankingRepository @Inject constructor(
    private val rankingService: ApiService
) {

    /**
     * Retrieves the list of player rankings.
     *
     * This suspend function fetches ranking data from the API through [rankingService] and
     * transforms it into a list of [RankingItem]. The transformation ensures that the data is
     * presented in a format suitable for the domain layer.
     *
     * @return A list of [RankingItem] representing player rankings.
     */
    suspend fun getRankings(): List<RankingItem> {
        return rankingService.getRankings().flatMap { it.toRankingItem() }
    }
}
