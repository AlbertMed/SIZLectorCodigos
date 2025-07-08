package com.albertmed.sizlectorcodigos.data

import com.albertmed.sizlectorcodigos.data.datastore.SessionManager
import com.albertmed.sizlectorcodigos.data.model.LoginRequest
import com.albertmed.sizlectorcodigos.data.model.LoginResponse
import com.albertmed.sizlectorcodigos.data.model.LoginEnvelope
import com.albertmed.sizlectorcodigos.data.model.SaveScanRequest
import com.albertmed.sizlectorcodigos.data.model.ScanData
import com.albertmed.sizlectorcodigos.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class UserRepository(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) {

    suspend fun login(empID: String, password: String): Response<LoginEnvelope> {
        val request = LoginRequest(empID, password)
        val response = apiService.login(request)
        if (response.isSuccessful) {
            val envelope = response.body()
            val user = envelope?.data
            if (user != null) {
                sessionManager.saveSession(empID, user.name, user.departamento)
            }
        }
        return response
    }

    suspend fun saveScannedData(userId: String, scannedData: String): Response<Unit> {
        val timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.format(Date())

        val request = SaveScanRequest(userId, scannedData, timestamp)
        return apiService.saveData(request)
    }

    suspend fun getScannedData(userId: String): Response<List<ScanData>> {
        return apiService.getData(userId)
    }

    suspend fun logout() {
        sessionManager.clearSession()
    }

    fun getUserId(): Flow<String?> = sessionManager.userIdFlow
    fun getUserName(): Flow<String?> = sessionManager.userNameFlow
    fun getUserDepartment(): Flow<String?> = sessionManager.userDepartmentFlow

} 