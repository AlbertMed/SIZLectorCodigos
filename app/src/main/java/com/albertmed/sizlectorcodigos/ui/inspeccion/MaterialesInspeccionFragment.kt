package com.albertmed.sizlectorcodigos.ui.inspeccion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.albertmed.sizlectorcodigos.databinding.FragmentMaterialesInspeccionBinding

class MaterialesInspeccionFragment : Fragment() {

    private var _binding: FragmentMaterialesInspeccionBinding? = null
    private val binding get() = _binding!!
    
    private val args: MaterialesInspeccionFragmentArgs by navArgs()
    private val viewModel: NuevaInspeccionViewModel by activityViewModels()
    private lateinit var materialesAdapter: MaterialesInspeccionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMaterialesInspeccionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupObservers()
        
        // Cargar los materiales usando el código escaneado
        viewModel.cargarEntradaMaterial(args.codigoEscaneado)
    }
    
    private fun setupRecyclerView() {
        materialesAdapter = MaterialesInspeccionAdapter { material ->
            // Navegar al fragmento de inspección del material
            val action = MaterialesInspeccionFragmentDirections.actionMaterialesInspeccionFragmentToInspeccionMaterialFragment(
                material.descripcion,
                material.proveedor
            )
            findNavController().navigate(action)
        }
        
        binding.recyclerViewMateriales.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = materialesAdapter
        }
    }
    
    private fun setupObservers() {
        viewModel.entradaMaterial.observe(viewLifecycleOwner) { entrada ->
            binding.apply {
                tvNumeroEntrada.text = "NUM. ENTRADA: ${entrada.numeroEntrada}"
                tvProveedor.text = "PROVEEDOR: ${entrada.proveedor}"
                tvFechaOc.text = "FECHA OC: ${viewModel.formatearFecha(entrada.fechaOc)}"
            }
            materialesAdapter.submitList(entrada.materiales)
        }
        
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 