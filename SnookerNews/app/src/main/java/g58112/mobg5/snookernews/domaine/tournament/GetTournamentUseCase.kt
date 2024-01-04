package g58112.mobg5.snookernews.domaine.tournament

import android.util.Log
import g58112.mobg5.snookernews.data.remote.modelTournament.Competition
import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import g58112.mobg5.snookernews.domaine.tournament.item.CompetitionItem
import g58112.mobg5.snookernews.repo.ranking.RankingRepository
import g58112.mobg5.snookernews.repo.tournament.TournamentRepository
import javax.inject.Inject

class GetTournamentUseCase @Inject constructor(
    private val tournamentRepository: TournamentRepository
){
    suspend operator fun invoke() : List<CompetitionItem> {
        Log.d("TAG", "invoke: " )
        return tournamentRepository.getTournaments()
    }
}