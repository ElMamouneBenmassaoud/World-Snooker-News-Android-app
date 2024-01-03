package g58112.mobg5.snookernews.domaine.item

import g58112.mobg5.snookernews.data.remote.model.CompetitorRanking
import g58112.mobg5.snookernews.data.remote.model.CompetitorRankingX
import g58112.mobg5.snookernews.data.remote.model.Ranking

data class RankingItem (
    val id: String,
    val name: String,
    val abbreviation: String,
    val rank: Int,
    val prizeMoney: Int
)

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