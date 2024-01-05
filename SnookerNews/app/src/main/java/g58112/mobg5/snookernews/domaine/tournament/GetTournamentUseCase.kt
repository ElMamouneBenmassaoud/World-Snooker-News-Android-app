package g58112.mobg5.snookernews.domaine.tournament

import g58112.mobg5.snookernews.domaine.tournament.item.CompetitionItem
import g58112.mobg5.snookernews.repo.tournament.TournamentRepository
import javax.inject.Inject

/**
 * Use case for fetching tournament information in the snooker news domain.
 *
 * This class encapsulates the logic for retrieving tournament data.
 * It serves as an intermediary between the repository layer and the UI/view model layer,
 * abstracting the details of data fetching and transformation.
 *
 * @property tournamentRepository The repository responsible for fetching tournament data.
 */
class GetTournamentUseCase @Inject constructor(
    private val tournamentRepository: TournamentRepository
) {

    /**
     * Retrieves the list of snooker tournaments.
     *
     * This suspend function is to be called within a coroutine or another suspend function.
     * It delegates the call to the [tournamentRepository] to fetch the tournament data and returns the result.
     *
     * @return A list of [CompetitionItem] representing the tournaments.
     */
    suspend operator fun invoke(): List<CompetitionItem> {
        return tournamentRepository.getTournaments()
    }
}
