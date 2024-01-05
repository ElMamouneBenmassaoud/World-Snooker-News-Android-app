package g58112.mobg5.snookernews.data.remote

import g58112.mobg5.snookernews.data.remote.modelRanking.TopRankingResponse
import g58112.mobg5.snookernews.data.remote.modelTournament.TopTournamentResponse
import g58112.mobg5.snookernews.util.Constant.RANKING_ENDPOINT
import g58112.mobg5.snookernews.util.Constant.TOURNAMENT_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for making API calls to fetch ranking and tournament data.
 */
interface QueryApi {

    /**
     * Fetches player rankings from the server.
     *
     * This is a suspend function and should be called from within a coroutine or another suspend function.
     *
     * @param apiKey The API key used for authorization to access the ranking data.
     * @return A [Response] object containing a [TopRankingResponse], which includes player rankings information.
     */
    @GET(RANKING_ENDPOINT)
    suspend fun getRankings(
        @Query("api_key") apiKey: String,
    ): Response<TopRankingResponse>

    /**
     * Fetches tournament data from the server.
     *
     * This is a suspend function and should be called from within a coroutine or another suspend function.
     *
     * @param apiKey The API key used for authorization to access the tournament data.
     * @return A [Response] object containing a [TopTournamentResponse], which includes information about various tournaments.
     */
    @GET(TOURNAMENT_ENDPOINT)
    suspend fun getTournaments(
        @Query("api_key") apiKey: String,
    ): Response<TopTournamentResponse>
}
