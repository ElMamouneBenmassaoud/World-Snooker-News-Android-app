package g58112.mobg5.snookernews.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName(value = "expires_at")
    val expiresAt: Int,

    @SerialName(value = "token_type")
    val createdAt: String
)
