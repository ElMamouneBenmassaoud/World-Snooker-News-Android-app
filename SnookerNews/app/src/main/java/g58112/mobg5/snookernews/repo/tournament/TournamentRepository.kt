package g58112.mobg5.snookernews.repo.tournament

import g58112.mobg5.snookernews.data.remote.ApiService
import g58112.mobg5.snookernews.domaine.tournament.item.CompetitionItem
import g58112.mobg5.snookernews.domaine.tournament.item.toCompetitionItem
import javax.inject.Inject

/**
 * Repository class for fetching tournament data.
 *
 * This class acts as a data layer for retrieving information about snooker tournaments, abstracting
 * the details of the data source and transformation. It interfaces with an API service to fetch data
 * and converts it into a format suitable for use in the domain layer.
 *
 * @property tournamentService The service used for making API calls to fetch tournament data.
 */
class TournamentRepository @Inject constructor(
    private val tournamentService: ApiService
) {

    /**
     * Retrieves the list of snooker tournaments.
     *
     * This suspend function fetches tournament data from the API through [tournamentService] and
     * transforms it into a list of [CompetitionItem]. The transformation ensures that the data is
     * presented in a format suitable for the domain layer.
     *
     * @return A list of [CompetitionItem] representing the tournaments. Returns an empty list if no data is fetched.
     */
    suspend fun getTournaments(): List<CompetitionItem> {
        val tournamentResponse = tournamentService.getTournaments()
        return tournamentResponse?.toCompetitionItem() ?: emptyList()
    }
}
