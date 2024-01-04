package g58112.mobg5.snookernews.data.remote

import g58112.mobg5.snookernews.data.remote.modelRanking.TopRankingResponse
import g58112.mobg5.snookernews.data.remote.modelTournament.TopTournamentResponse
import g58112.mobg5.snookernews.util.Constant.RANKING_ENDPOINT
import g58112.mobg5.snookernews.util.Constant.TOURNAMENT_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QueryApi {

    @GET(RANKING_ENDPOINT)
    suspend fun getRankings(
        @Query("api_key") apiKey: String,
    ): Response<TopRankingResponse>

    @GET(TOURNAMENT_ENDPOINT)
    suspend fun getTournaments(
        @Query("api_key") apiKey: String,
    ): Response<TopTournamentResponse>
}