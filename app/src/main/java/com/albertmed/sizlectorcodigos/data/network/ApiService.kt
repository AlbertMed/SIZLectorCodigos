package com.albertmed.sizlectorcodigos.data.network

import com.albertmed.sizlectorcodigos.data.model.LoginEnvelope
import com.albertmed.sizlectorcodigos.data.model.LoginRequest
import com.albertmed.sizlectorcodigos.data.model.LoginResponse
import com.albertmed.sizlectorcodigos.data.model.SaveScanRequest
import com.albertmed.sizlectorcodigos.data.model.ScanData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth-api")
    suspend fun login(@Body request: LoginRequest): Response<LoginEnvelope>

    @POST("data")
    suspend fun saveData(@Body request: SaveScanRequest): Response<Unit>

    @GET("data/{userId}")
    suspend fun getData(@Path("userId") userId: String): Response<List<ScanData>>
} 