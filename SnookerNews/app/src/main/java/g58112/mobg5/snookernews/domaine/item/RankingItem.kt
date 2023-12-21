package g58112.mobg5.snookernews.domaine.item

import g58112.mobg5.snookernews.data.remote.model.CompetitorRanking

data class RankingItem (
    val id: String,
    val name: String,
    val abbreviation: String,
    val rank: Int,
    val prizeMoney: Int
)

fun CompetitorRanking.toRankingItem() = RankingItem(
    id = competitor.id,
    name = competitor.name,
    abbreviation = competitor.abbreviation,
    rank = rank,
    prizeMoney = prizeMoney
)