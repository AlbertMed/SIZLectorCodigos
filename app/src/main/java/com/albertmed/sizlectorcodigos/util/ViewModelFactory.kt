package com.albertmed.sizlectorcodigos.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.albertmed.sizlectorcodigos.data.UserRepository
import com.albertmed.sizlectorcodigos.ui.login.LoginViewModel
import com.albertmed.sizlectorcodigos.ui.main.MainViewModel
import com.albertmed.sizlectorcodigos.ui.scanner.ScanResultViewModel
import com.albertmed.sizlectorcodigos.ui.home.HomeViewModel
import com.albertmed.sizlectorcodigos.ui.inspeccion.NuevaInspeccionViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ScanResultViewModel::class.java) -> {
                ScanResultViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NuevaInspeccionViewModel::class.java) -> {
                NuevaInspeccionViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
} 