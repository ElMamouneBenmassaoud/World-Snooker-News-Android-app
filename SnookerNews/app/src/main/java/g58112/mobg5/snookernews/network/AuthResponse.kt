package g58112.mobg5.snookernews.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("generated_at") val generatedAt: String,
)
