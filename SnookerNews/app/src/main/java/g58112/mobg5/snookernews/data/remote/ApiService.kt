package g58112.mobg5.snookernews.data.remote

import g58112.mobg5.snookernews.data.remote.modelRanking.Ranking
import g58112.mobg5.snookernews.data.remote.modelTournament.TopTournamentResponse
import g58112.mobg5.snookernews.util.Constant.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Service class for making API calls.
 *
 * @property QueryApi The Retrofit interface for making API requests.
 */
class ApiService @Inject constructor(
    private val QueryApi: QueryApi
) {
    /**
     * Fetches the list of player rankings.
     *
     * This function performs a network call to retrieve player rankings.
     * It operates asynchronously and should be called from within a coroutine.
     *
     * @return A list of [Ranking] objects representing the player rankings.
     */
    suspend fun getRankings(): List<Ranking> {
        return withContext(Dispatchers.IO) {

            val response = QueryApi.getRankings(
                apiKey = API_KEY
            )

            val rankingList = response.body()?.rankings
            rankingList ?: emptyList()
        }
    }

    /**
     * Retrieves the tournament response from the API.
     *
     * This method performs a network call to fetch information about tournaments.
     * It operates asynchronously and should be called from within a coroutine.
     *
     * @return A [TopTournamentResponse] object containing tournament data, or `null` if no tournaments are fetched.
     */
    suspend fun getTournaments(): TopTournamentResponse? {
        return withContext(Dispatchers.IO) {

            val response = QueryApi.getTournaments(
                apiKey = API_KEY
            )

            val tournamentList = response.body()
            tournamentList
        }
    }
}
