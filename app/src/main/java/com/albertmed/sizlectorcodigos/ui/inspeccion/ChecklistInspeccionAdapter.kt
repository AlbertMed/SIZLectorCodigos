package com.albertmed.sizlectorcodigos.ui.inspeccion

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.albertmed.sizlectorcodigos.R
import com.albertmed.sizlectorcodigos.data.model.EstadoInspeccion
import com.albertmed.sizlectorcodigos.data.model.ItemChecklist
import com.albertmed.sizlectorcodigos.databinding.ItemChecklistInspeccionBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.DataSource
import android.graphics.drawable.Drawable

class ChecklistInspeccionAdapter(
    private val onEstadoChanged: (ItemChecklist, EstadoInspeccion) -> Unit,
    private val onNotaVozClick: (ItemChecklist) -> Unit,
    private val onEvidenciaFotoClick: (ItemChecklist) -> Unit,
    private val onEliminarFotoClick: (ItemChecklist) -> Unit,
    private val getCantidadMaxima: (ItemChecklist) -> Double = { 100.0 }
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
            android.util.Log.d("ChecklistAdapter", "bind() llamado para item: ${item.nombre}, fotoUri: ${item.fotoUri}")
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

                btnNotaVoz.setOnClickListener { onNotaVozClick(item) }
                btnEvidenciaFoto.setOnClickListener { onEvidenciaFotoClick(item) }
                btnEliminarFoto.setOnClickListener { onEliminarFotoClick(item) }

                // Mostrar la foto y el botón de eliminar solo si hay foto
                val hayFoto = !item.fotoUri.isNullOrBlank()
                ivEvidenciaFoto.visibility = if (hayFoto) View.VISIBLE else View.GONE
                btnEliminarFoto.visibility = if (hayFoto) View.VISIBLE else View.GONE
                progressFoto.visibility = if (item.cargandoFoto) View.VISIBLE else View.GONE

                if (hayFoto) {
                    progressFoto.visibility = View.VISIBLE
                    android.util.Log.d("ChecklistAdapter", "Cargando imagen: ${item.fotoUri}")
                    Glide.with(ivEvidenciaFoto.context)
                        .load(item.fotoUri)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                android.util.Log.e("ChecklistAdapter", "Error cargando imagen: ${item.fotoUri}", e)
                                progressFoto.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any,
                                target: Target<Drawable>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                android.util.Log.d("ChecklistAdapter", "Imagen cargada correctamente: ${item.fotoUri}")
                                progressFoto.visibility = View.GONE
                                return false
                            }
                        })
                        .into(ivEvidenciaFoto)
                }

                // Bloquear interacción de los botones e input durante la carga de la foto
                val interactivo = !item.cargandoFoto
                btnCumplen.isEnabled = interactivo
                btnNoCumple.isEnabled = interactivo
                btnNoAplica.isEnabled = interactivo
                btnNotaVoz.isEnabled = interactivo
                btnEvidenciaFoto.isEnabled = interactivo
                btnEliminarFoto.isEnabled = interactivo
                etCantidad.isEnabled = interactivo

                // Mostrar input numérico solo si el estado es PASA (CUMPLEN)
                mostrarInputCantidad(item.estado == EstadoInspeccion.PASA, item)
            }
        }

        private fun mostrarInputCantidad(visible: Boolean, item: ItemChecklist) {
            val etCantidad = binding.etCantidad
            // Eliminar watcher anterior para evitar leaks y duplicados
            cantidadWatcher?.let { etCantidad.removeTextChangedListener(it) }
            if (visible) {
                etCantidad.visibility = View.VISIBLE
                val max = getCantidadMaxima(item)
                if (etCantidad.text.isNullOrBlank()) {
                    etCantidad.setText(max.toString())
                    item.observacion = max.toString()
                }
                etCantidad.setSelection(etCantidad.text?.length ?: 0)
                etCantidad.error = null
                cantidadWatcher = object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        val value = s?.toString()?.toDoubleOrNull()
                        if (value == null) {
                            etCantidad.error = "Obligatorio"
                        } else if (value < 0.1) {
                            etCantidad.error = "Mínimo 0.1"
                        } else if (value > max) {
                            etCantidad.error = "Máximo $max"
                        } else {
                            etCantidad.error = null
                            item.observacion = value.toString()
                        }
                    }
                }
                etCantidad.addTextChangedListener(cantidadWatcher)
            } else {
                etCantidad.visibility = View.GONE
                cantidadWatcher?.let { etCantidad.removeTextChangedListener(it) }
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