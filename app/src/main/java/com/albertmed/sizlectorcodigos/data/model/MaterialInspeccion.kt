package com.albertmed.sizlectorcodigos.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MaterialInspeccion(
    @SerialName("PROVEEDOR")
    val proveedor: String,
    @SerialName("DESCRIPCION")
    val descripcion: String,
    @SerialName("UDM")
    val udm: String,
    @SerialName("CANTIDAD")
    val cantidad: String,
    @SerialName("X_PAQ")
    val xPaq: String,
    @SerialName("FECHA_OC")
    val fechaOc: String
)

@Serializable
data class EntradaMaterialInspeccion(
    val numeroEntrada: String,
    val proveedor: String,
    val fechaOc: String,
    val materiales: List<MaterialInspeccion>
) 