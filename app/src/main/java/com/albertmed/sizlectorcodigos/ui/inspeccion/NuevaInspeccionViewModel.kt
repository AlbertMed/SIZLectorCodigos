package com.albertmed.sizlectorcodigos.ui.inspeccion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albertmed.sizlectorcodigos.data.model.EntradaMaterialInspeccion
import com.albertmed.sizlectorcodigos.data.model.MaterialInspeccion
import com.albertmed.sizlectorcodigos.data.network.RetrofitClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NuevaInspeccionViewModel : ViewModel() {
    
    private val _entradaMaterial = MutableLiveData<EntradaMaterialInspeccion>()
    val entradaMaterial: LiveData<EntradaMaterialInspeccion> = _entradaMaterial
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    fun cargarEntradaMaterial(codigoEscaneado: String) {
        _isLoading.value = true
        _error.value = null
        
        viewModelScope.launch {
            try {
                //quitar espacios
                val codigoEscaneado = codigoEscaneado.replace(" ", "")
                // checar si es un numero
                val id = codigoEscaneado.toBigInteger()
                if (id == null) {
                    _error.value = "El código escaneado no es un número válido: "+codigoEscaneado
                    return@launch
                }
                
                val response = RetrofitClient.instance.getEntradaMaterialInspeccion(id)
                
                if (response.isSuccessful) {
                    val materiales = response.body() ?: emptyList()
                    if (materiales.isNotEmpty()) {
                        val entrada = EntradaMaterialInspeccion(
                            numeroEntrada = codigoEscaneado,
                            proveedor = materiales.first().proveedor,
                            fechaOc = materiales.first().fechaOc,
                            materiales = materiales
                        )
                        _entradaMaterial.value = entrada
                    } else {
                        _error.value = "No se encontraron materiales para este código"
                    }
                } else {
                    _error.value = "Error al cargar los materiales: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun formatearFecha(fechaString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fecha = inputFormat.parse(fechaString)
            outputFormat.format(fecha ?: Date())
        } catch (e: Exception) {
            fechaString
        }
    }
} 