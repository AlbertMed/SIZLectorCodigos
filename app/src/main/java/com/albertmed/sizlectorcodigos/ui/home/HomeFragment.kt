package com.albertmed.sizlectorcodigos.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.albertmed.sizlectorcodigos.R
import com.albertmed.sizlectorcodigos.data.UserRepository
import com.albertmed.sizlectorcodigos.data.datastore.SessionManager
import com.albertmed.sizlectorcodigos.data.network.RetrofitClient
import com.albertmed.sizlectorcodigos.databinding.FragmentHomeBinding
import com.albertmed.sizlectorcodigos.util.ViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory(
            UserRepository(
                RetrofitClient.instance,
                SessionManager(requireContext())
            )
        )
    }
    private lateinit var scanAdapter: ScanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        binding.scanButton.setOnClickListener {
            findNavController().navigate(R.id.nav_scanner)
        }

        viewModel.fetchScannedData()
    }

    private fun setupRecyclerView() {
        scanAdapter = ScanAdapter()
        binding.scansRecyclerView.adapter = scanAdapter
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingIndicator.isVisible = isLoading
        }

        viewModel.scannedData.observe(viewLifecycleOwner) { result ->
            result.onSuccess { data ->
                val isEmpty = data.isEmpty()
                binding.emptyText.isVisible = isEmpty
                binding.scanButton.isVisible = isEmpty
                scanAdapter.submitList(data)
            }.onFailure {
                Toast.makeText(context, "Error al cargar los datos: ${it.message}", Toast.LENGTH_SHORT).show()
                binding.emptyText.isVisible = true
                binding.scanButton.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}