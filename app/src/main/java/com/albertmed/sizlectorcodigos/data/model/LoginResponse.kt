package com.albertmed.sizlectorcodigos.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val name: String,
    val departamento: String
)

@Serializable
data class LoginEnvelope(
    val status: String,
    val data: LoginResponse
) 