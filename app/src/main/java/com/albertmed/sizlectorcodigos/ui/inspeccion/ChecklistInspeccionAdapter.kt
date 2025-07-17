package com.albertmed.sizlectorcodigos.ui.inspeccion

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.albertmed.sizlectorcodigos.R
import com.albertmed.sizlectorcodigos.data.model.EstadoInspeccion
import com.albertmed.sizlectorcodigos.data.model.ItemChecklist
import com.albertmed.sizlectorcodigos.databinding.ItemChecklistInspeccionBinding

class ChecklistInspeccionAdapter(
    private val onEstadoChanged: (ItemChecklist, EstadoInspeccion) -> Unit,
    private val onNotaVozClick: (ItemChecklist) -> Unit,
    private val onEvidenciaFotoClick: (ItemChecklist) -> Unit,
    private val onEliminarFotoClick: (ItemChecklist) -> Unit,
    private val getCantidadMaxima: (ItemChecklist) -> Double = { 100.0 } // Por defecto 100, puedes personalizarlo
) : ListAdapter<ItemChecklist, ChecklistInspeccionAdapter.ChecklistViewHolder>(ChecklistDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val binding = ItemChecklistInspeccionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChecklistViewHolder(binding, onEstadoChanged, onNotaVozClick, onEvidenciaFotoClick, onEliminarFotoClick, getCantidadMaxima)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChecklistViewHolder(
        private val binding: ItemChecklistInspeccionBinding,
        private val onEstadoChanged: (ItemChecklist, EstadoInspeccion) -> Unit,
        private val onNotaVozClick: (ItemChecklist) -> Unit,
        private val onEvidenciaFotoClick: (ItemChecklist) -> Unit,
        private val onEliminarFotoClick: (ItemChecklist) -> Unit,
        private val getCantidadMaxima: (ItemChecklist) -> Double
    ) : RecyclerView.ViewHolder(binding.root) {

        private var cantidadWatcher: TextWatcher? = null

        fun bind(item: ItemChecklist) {
            binding.apply {
                tvNombreItem.text = item.nombre

                // Estado visual de los botones
                updateButtonStates(item.estado)

                btnCumplen.setOnClickListener {
                    onEstadoChanged(item, EstadoInspeccion.PASA)
                    updateButtonStates(EstadoInspeccion.PASA)
                    mostrarInputCantidad(true, item)
                }
                btnNoCumple.setOnClickListener {
                    onEstadoChanged(item, EstadoInspeccion.NO_PASA)
                    updateButtonStates(EstadoInspeccion.NO_PASA)
                    mostrarInputCantidad(false, item)
                }
                btnNoAplica.setOnClickListener {
                    onEstadoChanged(item, EstadoInspeccion.NO_APLICA)
                    updateButtonStates(EstadoInspeccion.NO_APLICA)
                    mostrarInputCantidad(false, item)
                }

                // Botón de nota de voz
                btnNotaVoz.setOnClickListener {
                    onNotaVozClick(item)
                }
                // Botón de evidencia fotográfica
                btnEvidenciaFoto.setOnClickListener {
                    onEvidenciaFotoClick(item)
                }
                // Botón de eliminar foto
                btnEliminarFoto.setOnClickListener {
                    onEliminarFotoClick(item)
                }

                // Mostrar la foto si existe
                if (!item.fotoUri.isNullOrBlank()) {
                    ivEvidenciaFoto.visibility = View.VISIBLE
                    btnEliminarFoto.visibility = View.VISIBLE
                    ivEvidenciaFoto.setImageURI(android.net.Uri.parse(item.fotoUri))
                } else {
                    ivEvidenciaFoto.visibility = View.GONE
                    btnEliminarFoto.visibility = View.GONE
                }

                // Mostrar input numérico solo si el estado es PASA (CUMPLEN)
                mostrarInputCantidad(item.estado == EstadoInspeccion.PASA, item)
            }
        }

        private fun mostrarInputCantidad(visible: Boolean, item: ItemChecklist) {
            val etCantidad = binding.etCantidad
            if (visible) {
                etCantidad.visibility = View.VISIBLE
                // Valor por defecto: cantidad máxima del material
                val max = getCantidadMaxima(item)
                if (etCantidad.text.isNullOrBlank()) {
                    etCantidad.setText(max.toString())
                    item.observacion = max.toString()
                }
                etCantidad.setSelection(etCantidad.text?.length ?: 0)
                etCantidad.error = null
                etCantidad.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        val value = s?.toString()?.toDoubleOrNull() ?: 0.0
                        if (value < 0.1) {
                            etCantidad.error = "Mínimo 0.1"
                        } else if (value > max) {
                            etCantidad.error = "Máximo $max"
                        } else {
                            etCantidad.error = null
                            item.observacion = value.toString()
                        }
                    }
                })
            } else {
                etCantidad.visibility = View.GONE
            }
        }

        private fun updateButtonStates(estado: EstadoInspeccion) {
            val context = binding.root.context
            val primary = ContextCompat.getColor(context, R.color.gray_primary)
            val neutral = ContextCompat.getColor(context, android.R.color.darker_gray)
            binding.btnCumplen.setBackgroundColor(if (estado == EstadoInspeccion.PASA) primary else neutral)
            binding.btnNoCumple.setBackgroundColor(if (estado == EstadoInspeccion.NO_PASA) primary else neutral)
            binding.btnNoAplica.setBackgroundColor(if (estado == EstadoInspeccion.NO_APLICA) primary else neutral)
            binding.btnCumplen.setTextColor(ContextCompat.getColor(context, android.R.color.white))
            binding.btnNoCumple.setTextColor(ContextCompat.getColor(context, android.R.color.white))
            binding.btnNoAplica.setTextColor(ContextCompat.getColor(context, android.R.color.white))
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