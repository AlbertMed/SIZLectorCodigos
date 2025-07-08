package com.albertmed.sizlectorcodigos.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val empID: String,
    val password: String
) 