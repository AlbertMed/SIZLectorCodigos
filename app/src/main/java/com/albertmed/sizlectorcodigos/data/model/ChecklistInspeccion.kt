package com.albertmed.sizlectorcodigos.data.model

enum class EstadoInspeccion {
    PASA,
    NO_PASA,
    NO_APLICA
}

data class ItemChecklist(
    val id: String,
    val nombre: String,
    var estado: EstadoInspeccion = EstadoInspeccion.NO_APLICA,
    var observacion: String = "",
    val cantidadMaxima: Double = 0.0,
    var fotoUri: String? = null, // URI de la foto de evidencia
    var cargandoFoto: Boolean = false // Estado de carga de la foto
)

data class InspeccionMaterial(
    val materialId: String,
    val descripcion: String,
    val checklist: List<ItemChecklist>,
    val fechaInspeccion: String = "",
    val inspector: String = ""
) 