package g58112.mobg5.snookernews.network

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody (
    val email: String,
    val password: String
)