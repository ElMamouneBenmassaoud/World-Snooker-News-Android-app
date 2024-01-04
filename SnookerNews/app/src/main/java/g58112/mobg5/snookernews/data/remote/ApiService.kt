package g58112.mobg5.snookernews.data.remote

import android.util.Log
import g58112.mobg5.snookernews.data.remote.modelRanking.Ranking
import g58112.mobg5.snookernews.data.remote.modelTournament.Competition
import g58112.mobg5.snookernews.data.remote.modelTournament.TopTournamentResponse
import g58112.mobg5.snookernews.util.Constant.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiService @Inject constructor(
    private val QueryApi: QueryApi
) {
    suspend fun getRankings(): List<Ranking> {
        return withContext(Dispatchers.IO) {

            val response = QueryApi.getRankings(
                apiKey = API_KEY
            )

            val rankingList = response.body()?.rankings
            Log.d("Ranking", "getRankings: $rankingList")
            rankingList ?: emptyList()
        }
    }

    suspend fun getTournaments(): TopTournamentResponse? {
        return withContext(Dispatchers.IO) {

            val response = QueryApi.getTournaments(
                apiKey = API_KEY
            )

            val tournamentList = response.body()

            Log.d("TEST", "getTournaments: $tournamentList")
            tournamentList
        }
    }
}
