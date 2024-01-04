package g58112.mobg5.snookernews.data.remote.ranking.model

data class Ranking(
    val competitor_rankings: List<CompetitorRanking>,
    val name: String,
    val type_id: Int,
    val week: Int,
    val year: Int
)