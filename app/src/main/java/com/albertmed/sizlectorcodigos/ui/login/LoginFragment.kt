package com.albertmed.sizlectorcodigos.ui.login

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
import com.albertmed.sizlectorcodigos.databinding.FragmentLoginBinding
import com.albertmed.sizlectorcodigos.util.ViewModelFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels {
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            val empID = binding.userIdInput.text.toString()
            val password = binding.passwordInput.text.toString()
            // Basic validation
            if (empID.isNotBlank() && password.isNotBlank()) {
                viewModel.login(empID, password)
            } else {
                Toast.makeText(context, "Por favor, introduce usuario y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        binding.continueButton.setOnClickListener {
            // Navigate to home/scanner as a guest
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingIndicator.isVisible = isLoading
            binding.loginButton.isEnabled = !isLoading
            binding.continueButton.isEnabled = !isLoading
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { loginResponse ->
                Toast.makeText(context, "Bienvenido, ${loginResponse.nombre}", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }.onFailure {
                Toast.makeText(context, "Error de autenticación", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 