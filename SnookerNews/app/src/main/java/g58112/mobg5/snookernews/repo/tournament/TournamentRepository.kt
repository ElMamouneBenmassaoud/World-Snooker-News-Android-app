package g58112.mobg5.snookernews.repo.tournament

import android.util.Log
import g58112.mobg5.snookernews.data.remote.ApiService
import g58112.mobg5.snookernews.domaine.tournament.item.CompetitionItem
import g58112.mobg5.snookernews.domaine.tournament.item.toCompetitionItem
import javax.inject.Inject

class TournamentRepository @Inject constructor(
    private val tournamentService: ApiService
) {
    suspend fun getTournaments(): List<CompetitionItem> {
        val tournamentResponse = tournamentService.getTournaments()
        return tournamentResponse?.toCompetitionItem() ?: emptyList()
    }
}