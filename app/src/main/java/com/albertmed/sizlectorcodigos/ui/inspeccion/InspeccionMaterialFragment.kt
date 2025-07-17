package com.albertmed.sizlectorcodigos.ui.inspeccion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.albertmed.sizlectorcodigos.R
import com.albertmed.sizlectorcodigos.data.model.EstadoInspeccion
import com.albertmed.sizlectorcodigos.data.model.ItemChecklist
import com.albertmed.sizlectorcodigos.databinding.FragmentInspeccionMaterialBinding

class InspeccionMaterialFragment : Fragment() {

    private var _binding: FragmentInspeccionMaterialBinding? = null
    private val binding get() = _binding!!
    
    private val args: InspeccionMaterialFragmentArgs by navArgs()
    private lateinit var checklistAdapter: ChecklistInspeccionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInspeccionMaterialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupRecyclerView()
        setupButtons()
    }
    
    private fun setupUI() {
        binding.apply {
            tvDescripcionMaterial.text = args.descripcionMaterial
            tvProveedor.text = "Proveedor: ${args.proveedor}"
            tvCantidad.text = "Cantidad: ${args.cantidadMaterial}"
        }
    }
    
    private fun setupRecyclerView() {
        val arguments = arguments
        arguments.toString()

        val cantidadMaterial = arguments?.getString("cantidadMaterial")?.toDoubleOrNull() ?: 0.0
        val checklistItems = listOf(
            ItemChecklist("1", "Dimensiones", cantidadMaxima = cantidadMaterial),
            ItemChecklist("2", "Acabado", cantidadMaxima = cantidadMaterial),
            ItemChecklist("3", "Espesor", cantidadMaxima = cantidadMaterial),
            ItemChecklist("4", "Densidad", cantidadMaxima = cantidadMaterial),
            ItemChecklist("5", "Elasticidad", cantidadMaxima = cantidadMaterial),
            ItemChecklist("6", "Adherencia", cantidadMaxima = cantidadMaterial),
            ItemChecklist("7", "Resistencia", cantidadMaxima = cantidadMaterial),
            ItemChecklist("8", "Gramage", cantidadMaxima = cantidadMaterial),
            ItemChecklist("9", "Templado", cantidadMaxima = cantidadMaterial),
            ItemChecklist("10", "Grietas", cantidadMaxima = cantidadMaterial),
            ItemChecklist("11", "Humedad", cantidadMaxima = cantidadMaterial),
            ItemChecklist("12", "Detalles visuales", cantidadMaxima = cantidadMaterial),
            ItemChecklist("13", "Funcionalidad", cantidadMaxima = cantidadMaterial)
        )
        
        checklistAdapter = ChecklistInspeccionAdapter(
            onEstadoChanged = { item, estado ->
                item.estado = estado
            },
            onNotaVozClick = { item ->
                // TODO: lógica para grabar nota de voz para este rubro
            },
            onEvidenciaFotoClick = { item ->
                // TODO: lógica para tomar foto para este rubro
            },
            getCantidadMaxima = { it.cantidadMaxima }
        )
        
        binding.recyclerViewChecklist.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = checklistAdapter
        }
        
        checklistAdapter.submitList(checklistItems)
    }
    
    private fun setupButtons() {
        binding.btnGuardarInspeccion.setOnClickListener {
            guardarInspeccion()
        }
    }
    
    private fun guardarInspeccion() {
        val items = checklistAdapter.currentList
        val itemsCompletados = items.all { it.estado != EstadoInspeccion.NO_APLICA }
        
        if (!itemsCompletados) {
            Toast.makeText(context, getString(R.string.completar_checklist), Toast.LENGTH_SHORT).show()
            return
        }
        
        // Aquí se guardaría la inspección en la base de datos o se enviaría al servidor
        Toast.makeText(context, getString(R.string.inspeccion_guardada), Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 