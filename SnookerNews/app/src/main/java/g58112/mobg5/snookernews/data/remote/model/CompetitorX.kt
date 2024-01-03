package g58112.mobg5.snookernews.data.remote.model

data class CompetitorX(
    val abbreviation: String,
    val id: String,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}