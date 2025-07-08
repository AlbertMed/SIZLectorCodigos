package com.albertmed.sizlectorcodigos.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albertmed.sizlectorcodigos.data.UserRepository
import com.albertmed.sizlectorcodigos.data.model.ScanData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _scannedData = MutableLiveData<Result<List<ScanData>>>()
    val scannedData: LiveData<Result<List<ScanData>>> = _scannedData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchScannedData() {
        viewModelScope.launch {
            _isLoading.value = true
            val userId = userRepository.getUserId().first()
            if (userId == null) {
                _scannedData.value = Result.success(emptyList()) // Or failure
                _isLoading.value = false
                return@launch
            }

            try {
                val response = userRepository.getScannedData(userId)
                if (response.isSuccessful) {
                    _scannedData.postValue(Result.success(response.body() ?: emptyList()))
                } else {
                    _scannedData.postValue(Result.failure(Throwable("Failed to fetch data")))
                }
            } catch (e: Exception) {
                _scannedData.postValue(Result.failure(e))
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
} 