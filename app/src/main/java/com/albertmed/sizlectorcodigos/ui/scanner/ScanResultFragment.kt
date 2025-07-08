package com.albertmed.sizlectorcodigos.ui.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.albertmed.sizlectorcodigos.R
import com.albertmed.sizlectorcodigos.data.UserRepository
import com.albertmed.sizlectorcodigos.data.datastore.SessionManager
import com.albertmed.sizlectorcodigos.data.network.RetrofitClient
import com.albertmed.sizlectorcodigos.databinding.FragmentScanResultBinding
import com.albertmed.sizlectorcodigos.util.ViewModelFactory

class ScanResultFragment : Fragment() {

    private var _binding: FragmentScanResultBinding? = null
    private val binding get() = _binding!!

    private val args: ScanResultFragmentArgs by navArgs()

    private val viewModel: ScanResultViewModel by viewModels {
        ViewModelFactory(
            UserRepository(
                RetrofitClient.instance,
                SessionManager(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scannedDataText.text = args.scannedData

        setupObservers()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.scanAgainButton.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.nav_host_fragment_content_main)
            navController.navigate(R.id.nav_scanner)
        }
        binding.saveButton.setOnClickListener {
            viewModel.saveData(args.scannedData)
        }
    }

    private fun setupObservers() {
        viewModel.userId.observe(viewLifecycleOwner) { userId ->
            binding.saveButton.isVisible = userId != null
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.saveButton.isEnabled = !isLoading
            binding.scanAgainButton.isEnabled = !isLoading
        }

        viewModel.saveResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(context, "Datos guardados con éxito", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }.onFailure {
                Toast.makeText(context, "Error al guardar los datos: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 