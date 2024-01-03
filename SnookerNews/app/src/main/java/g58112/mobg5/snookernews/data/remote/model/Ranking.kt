package g58112.mobg5.snookernews.data.remote.model

data class Ranking(
    val competitor_rankings: List<CompetitorRankingX>,
    val name: String,
    val type_id: Int,
    val week: Int,
    val year: Int
)