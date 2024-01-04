package g58112.mobg5.snookernews.data.remote.ranking.model

data class TopRankingResponse(
    val generated_at: String,
    val rankings: List<Ranking>
)