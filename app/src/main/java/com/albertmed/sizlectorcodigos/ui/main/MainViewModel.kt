package com.albertmed.sizlectorcodigos.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.albertmed.sizlectorcodigos.data.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    val userName: LiveData<String?> = userRepository.getUserName().asLiveData()
    val userId: LiveData<String?> = userRepository.getUserId().asLiveData()

    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.logout()
        }
    }
} 