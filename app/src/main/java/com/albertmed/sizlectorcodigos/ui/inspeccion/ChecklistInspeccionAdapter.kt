package com.albertmed.sizlectorcodigos.ui.inspeccion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.albertmed.sizlectorcodigos.data.model.EstadoInspeccion
import com.albertmed.sizlectorcodigos.data.model.ItemChecklist
import com.albertmed.sizlectorcodigos.databinding.ItemChecklistInspeccionBinding

class ChecklistInspeccionAdapter(
    private val onEstadoChanged: (ItemChecklist, EstadoInspeccion) -> Unit
) : ListAdapter<ItemChecklist, ChecklistInspeccionAdapter.ChecklistViewHolder>(ChecklistDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val binding = ItemChecklistInspeccionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChecklistViewHolder(binding, onEstadoChanged)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChecklistViewHolder(
        private val binding: ItemChecklistInspeccionBinding,
        private val onEstadoChanged: (ItemChecklist, EstadoInspeccion) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemChecklist) {
            binding.apply {
                tvNombreItem.text = item.nombre
                etObservacion.setText(item.observacion)
                
                // Configurar botones de estado
                btnPasa.setOnClickListener {
                    onEstadoChanged(item, EstadoInspeccion.PASA)
                    updateButtonStates(EstadoInspeccion.PASA)
                }
                
                btnNoPasa.setOnClickListener {
                    onEstadoChanged(item, EstadoInspeccion.NO_PASA)
                    updateButtonStates(EstadoInspeccion.NO_PASA)
                }
                
                btnNoAplica.setOnClickListener {
                    onEstadoChanged(item, EstadoInspeccion.NO_APLICA)
                    updateButtonStates(EstadoInspeccion.NO_APLICA)
                }
                
                // Configurar el estado actual
                updateButtonStates(item.estado)
                
                // Configurar el listener para la observación
                etObservacion.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        item.observacion = etObservacion.text.toString()
                    }
                }
            }
        }
        
        private fun updateButtonStates(estado: EstadoInspeccion) {
            binding.apply {
                btnPasa.isSelected = estado == EstadoInspeccion.PASA
                btnNoPasa.isSelected = estado == EstadoInspeccion.NO_PASA
                btnNoAplica.isSelected = estado == EstadoInspeccion.NO_APLICA
            }
        }
    }

    private class ChecklistDiffCallback : DiffUtil.ItemCallback<ItemChecklist>() {
        override fun areItemsTheSame(oldItem: ItemChecklist, newItem: ItemChecklist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemChecklist, newItem: ItemChecklist): Boolean {
            return oldItem == newItem
        }
    }
} 