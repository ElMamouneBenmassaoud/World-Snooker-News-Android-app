package com.example.marsphotos.network

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody (
    val email: String,
    val password: String
)