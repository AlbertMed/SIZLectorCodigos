package com.albertmed.sizlectorcodigos.ui.inspeccion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.albertmed.sizlectorcodigos.data.model.MaterialInspeccion
import com.albertmed.sizlectorcodigos.databinding.ItemMaterialInspeccionBinding
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class MaterialesInspeccionAdapter(
    private val onMaterialClick: (MaterialInspeccion) -> Unit
) : ListAdapter<MaterialInspeccion, MaterialesInspeccionAdapter.MaterialViewHolder>(MaterialDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialViewHolder {
        val binding = ItemMaterialInspeccionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MaterialViewHolder(binding, onMaterialClick)
    }

    override fun onBindViewHolder(holder: MaterialViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MaterialViewHolder(
        private val binding: ItemMaterialInspeccionBinding,
        private val onMaterialClick: (MaterialInspeccion) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(material: MaterialInspeccion) {
            binding.apply {
                tvDescripcion.text = material.descripcion
                tvUdm.text = "UDM: ${material.udm}"
                tvCantidad.text = "Cantidad: ${material.cantidad}"
                // tvXPaq.text = "X_Paquete: ${material.xPaq}"
                
                root.setOnClickListener {
                    // Navegar al fragmento de inspección del material pasando la cantidad
                    val action = MaterialesInspeccionFragmentDirections.actionMaterialesInspeccionFragmentToInspeccionMaterialFragment(
                        material.descripcion,
                        material.proveedor,
                        material.cantidad // <-- pasa la cantidad como string
                    )
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    private class MaterialDiffCallback : DiffUtil.ItemCallback<MaterialInspeccion>() {
        override fun areItemsTheSame(oldItem: MaterialInspeccion, newItem: MaterialInspeccion): Boolean {
            return oldItem.descripcion == newItem.descripcion
        }

        override fun areContentsTheSame(oldItem: MaterialInspeccion, newItem: MaterialInspeccion): Boolean {
            return oldItem == newItem
        }
    }
} 