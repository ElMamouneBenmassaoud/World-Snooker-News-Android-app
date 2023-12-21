package g58112.mobg5.snookernews.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompetitorRanking(

    val rank: Int,
    @SerialName(value = "prize_money")
    val prizeMoney: Int,
    val competitor: CompetitorDetails
)

@Serializable
data class CompetitorDetails(
    val id: String,
    val name: String,
    val abbreviation: String
)