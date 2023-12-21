package g58112.mobg5.snookernews.data.remote

import g58112.mobg5.snookernews.data.remote.model.CompetitorRanking
import g58112.mobg5.snookernews.util.Constant.RANKING_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RankingApi {

    @GET(RANKING_ENDPOINT) //.json
    suspend fun getRankings(
        @Query("api_key") apiKey: String,
    ): Response<List<CompetitorRanking>>
}