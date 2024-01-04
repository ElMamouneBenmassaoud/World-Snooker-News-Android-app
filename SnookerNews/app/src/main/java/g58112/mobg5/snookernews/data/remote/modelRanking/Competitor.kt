package g58112.mobg5.snookernews.data.remote.modelRanking

data class Competitor(
    val abbreviation: String,
    val id: String,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}