package com.albertmed.sizlectorcodigos.ui.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.albertmed.sizlectorcodigos.data.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ScanResultViewModel(private val userRepository: UserRepository) : ViewModel() {

    val userId: LiveData<String?> = userRepository.getUserId().asLiveData()

    private val _saveResult = MutableLiveData<Result<Unit>>()
    val saveResult: LiveData<Result<Unit>> = _saveResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveData(scannedData: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val currentUserId = userRepository.getUserId().first()
            if (currentUserId == null) {
                _saveResult.value = Result.failure(IllegalStateException("User not logged in"))
                _isLoading.value = false
                return@launch
            }

            try {
                val response = userRepository.saveScannedData(currentUserId, scannedData)
                if (response.isSuccessful) {
                    _saveResult.postValue(Result.success(Unit))
                } else {
                    _saveResult.postValue(Result.failure(Throwable("Failed to save data")))
                }
            } catch (e: Exception) {
                _saveResult.postValue(Result.failure(e))
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
} 