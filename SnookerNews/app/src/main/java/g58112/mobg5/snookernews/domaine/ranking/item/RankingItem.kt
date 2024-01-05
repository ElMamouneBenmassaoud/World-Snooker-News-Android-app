package g58112.mobg5.snookernews.domaine.ranking.item

import g58112.mobg5.snookernews.data.remote.modelRanking.Ranking

/**
 * Represents a ranking item in snooker news domain.
 *
 * This data class encapsulates the details of a player's ranking.
 *
 * @property id The unique identifier of the ranking.
 * @property name The name of the player.
 * @property abbreviation The abbreviation of the player's name.
 * @property rank The rank of the player.
 * @property prizeMoney The prize money earned by the player.
 */
data class RankingItem (
    val id: String,
    val name: String,
    val abbreviation: String,
    val rank: Int,
    val prizeMoney: Int
)

/**
 * Converts a [Ranking] object (from remote model) to a list of [RankingItem].
 *
 * This extension function maps the data from a [Ranking] object, which is typically
 * retrieved from an API response, to a list of more domain-specific [RankingItem] objects.
 *
 * @receiver The [Ranking] object to be transformed.
 * @return A list of [RankingItem] representing the player rankings.
 */
fun Ranking.toRankingItem(): List<RankingItem> {
    return this.competitor_rankings.map { competitorRanking ->
        RankingItem(
            id = competitorRanking.competitor.id,
            name = competitorRanking.competitor.name,
            abbreviation = competitorRanking.competitor.abbreviation,
            rank = competitorRanking.rank,
            prizeMoney = competitorRanking.prize_money
        )
    }
}
