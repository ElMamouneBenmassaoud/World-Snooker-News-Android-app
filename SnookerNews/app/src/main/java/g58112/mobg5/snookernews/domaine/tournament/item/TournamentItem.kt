package g58112.mobg5.snookernews.domaine.tournament.item

import g58112.mobg5.snookernews.data.remote.modelTournament.TopTournamentResponse

/**
 * Represents a competition item in the snooker news domain.
 *
 * This data class encapsulates the details of a snooker competition.
 *
 * @property id The unique identifier of the competition (nullable).
 * @property name The name of the competition (nullable).
 * @property gender The gender category of the competition (nullable).
 * @property countryCode The country code associated with the competition (nullable).
 * @property nameCategory The name of the competition's category (nullable).
 */
data class CompetitionItem(
    val id: String?,
    val name: String?,
    val gender: String?,
    val countryCode: String?,
    val nameCategory: String?
)

/**
 * Converts a [TopTournamentResponse] object to a list of [CompetitionItem].
 *
 * This extension function maps the data from a [TopTournamentResponse] object, which is typically
 * retrieved from an API response, to a list of more domain-specific [CompetitionItem] objects.
 *
 * @receiver The [TopTournamentResponse] object to be transformed.
 * @return A list of [CompetitionItem] representing the competitions.
 */
fun TopTournamentResponse.toCompetitionItem(): List<CompetitionItem> {
    return this.competitions.map { competition ->
        CompetitionItem(
            id = competition.id,
            name = competition.name,
            gender = competition.gender,
            countryCode = competition.category.country_code,
            nameCategory = competition.category.name
        )
    }
}
