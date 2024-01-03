package g58112.mobg5.snookernews.data.remote

import g58112.mobg5.snookernews.data.remote.model.CompetitorRanking
import g58112.mobg5.snookernews.data.remote.model.CompetitorRankingX
import g58112.mobg5.snookernews.data.remote.model.Ranking
import g58112.mobg5.snookernews.data.remote.model.test
import g58112.mobg5.snookernews.util.Constant.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import javax.inject.Inject

class RankingService @Inject constructor(
    private val rankingApi: RankingApi
) {
    suspend fun getRankings(): List<Ranking> {
        return withContext(Dispatchers.IO) {
            logger.warning("getRankings() before response: ")

            val response = rankingApi.getRankings(
                apiKey = API_KEY
            )

            val rankingList = response.body()?.rankings

            //logger.warning("getRankings() response: ${response.message()} ${response.code()} ${response.body()} ${response.errorBody()}")
            logger.warning("getRankings() response: $rankingList")

            //emptyList()
            rankingList ?: emptyList()
        }
    }
}
