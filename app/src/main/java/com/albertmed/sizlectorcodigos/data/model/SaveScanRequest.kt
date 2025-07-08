package com.albertmed.sizlectorcodigos.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SaveScanRequest(
    val userId: String,
    val scannedData: String,
    val timestamp: String
) 