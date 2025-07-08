package com.albertmed.sizlectorcodigos.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ScanData(
    val scannedData: String,
    val timestamp: String
) 