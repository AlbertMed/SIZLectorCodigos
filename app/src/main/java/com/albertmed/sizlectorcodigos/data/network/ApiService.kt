package com.albertmed.sizlectorcodigos.data.network

import com.albertmed.sizlectorcodigos.data.model.LoginEnvelope
import com.albertmed.sizlectorcodigos.data.model.LoginRequest
import com.albertmed.sizlectorcodigos.data.model.LoginResponse
import com.albertmed.sizlectorcodigos.data.model.SaveScanRequest
import com.albertmed.sizlectorcodigos.data.model.ScanData
import com.albertmed.sizlectorcodigos.data.model.MaterialInspeccion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.math.BigInteger

interface ApiService {
    @POST("api/auth-login")
    suspend fun login(@Body request: LoginRequest): Response<LoginEnvelope>
    
    @POST("data")
    suspend fun saveData(@Body request: SaveScanRequest): Response<Unit>

    @GET("data/{userId}")
    suspend fun getData(@Path("userId") userId: String): Response<List<ScanData>>
    
    @GET("api/entrada_material_inspeccion/{id}")
    suspend fun getEntradaMaterialInspeccion(@Path("id") id: BigInteger): Response<List<MaterialInspeccion>>
} 