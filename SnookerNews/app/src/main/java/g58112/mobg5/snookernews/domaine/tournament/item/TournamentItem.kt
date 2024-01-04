package g58112.mobg5.snookernews.domaine.tournament.item

import android.util.Log
import g58112.mobg5.snookernews.data.remote.modelTournament.Competition
import g58112.mobg5.snookernews.data.remote.modelTournament.TopTournamentResponse

data class CompetitionItem(
    val id: String?,
    val name: String?,
    val gender: String?,
    val country_code: String?,
    val nameCategory: String?
)

fun TopTournamentResponse.toCompetitionItem(): List<CompetitionItem> {
    Log.d("Tag", "ici ${this.competitions}")
    return this.competitions.map { competition ->
        Log.d("TAG", "toCompetitionItem: ${competition.name}")
        CompetitionItem(
            id = competition.id,
            name = competition.name,
            gender = competition.gender,
            country_code = competition.category.country_code,
            nameCategory = competition.category.name
        )
    }
}
