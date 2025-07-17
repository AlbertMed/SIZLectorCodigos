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
    var observacion: String = ""
)

data class InspeccionMaterial(
    val materialId: String,
    val descripcion: String,
    val checklist: List<ItemChecklist>,
    val fechaInspeccion: String = "",
    val inspector: String = ""
) 